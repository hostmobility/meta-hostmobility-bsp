#include <errno.h>
#include <getopt.h>
#include <libgen.h>
#include <limits.h>
#include <poll.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <net/if.h>

#include <sys/ioctl.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/uio.h>

#include <linux/flexray.h>
#include <linux/flexray/raw.h>

extern int optind, opterr, optopt;

static int s = -1;
static int running = 1;

#ifndef VERSION
#define VERSION "5.0"
#endif
#ifndef PF_FLEXRAY
#define PF_FLEXRAY 40
#endif

enum {
   VERSION_OPTION = CHAR_MAX + 1,
};

#define FRAME_ID_DEFAULT   (2)
#define FLEXRAY_DLC_DEFAULT   (64)
#define MAX_FLEXRAY_DLC      (127)

void print_usage(char *prg)
{
   fprintf(stderr, "Usage: %s [<flexray-interface>] [Options]\n"
      "\n"
      "flexraysequence sends FLEXRAY messages with a rising sequence number as payload.\n"
      "When the -r option is given, flexraysequence expects to receive these messages\n"
      "and prints an error message if a wrong sequence number is encountered.\n"
      "The main purpose of this program is to test the reliability of FLEXRAY links.\n"
      "\n"
      "Options:\n"
      " -d, --dlc=1-%d      FLEXRAY data length (default = %u)\n"
      " -i, --identifier=ID   FLEXRAY Identifier (default = %u)\n"
      " -r, --receive      work as receiver\n"
      "     --loop=COUNT   send message COUNT times\n"
      " -p  --poll      use poll(2) to wait for buffer space while sending\n"
      " -q  --quit      quit if a wrong sequence is encountered\n"
      " -v, --verbose      be verbose (twice to be even more verbose\n"
      " -h  --help      this help\n"
      "     --version      print version information and exit\n",
      prg, MAX_FLEXRAY_DLC, FLEXRAY_DLC_DEFAULT, FRAME_ID_DEFAULT);
}

void sigterm(int signo)
{
   running = 0;
}

int main(int argc, char **argv)
{
   struct ifreq ifr;
   struct sockaddr_flexray addr;
   struct flexray_frame frame = {
      .frhead.plr = FLEXRAY_DLC_DEFAULT,
   };

   struct flexray_filter filter[] = {
      {
         .flexray_id = FRAME_ID_DEFAULT,
      }
   };
   char *interface = "vflexray0";
   unsigned char sequence = 0;
   int seq_wrap = 0;
   int family = PF_FLEXRAY, type = SOCK_RAW, proto = FLEXRAY_RAW;
   int loopcount = 1, infinite = 1;
   int use_poll = 0;
   int nbytes;
   int opt;
   int receive = 0;
   int sequence_init = 1;
   int verbose = 0, quit = 0;
   int exit_value = EXIT_SUCCESS;

   signal(SIGTERM, sigterm);
   signal(SIGHUP, sigterm);

   struct option long_options[] = {
      { "help",   no_argument,      0, 'h' },
      { "poll",   no_argument,      0, 'p' },
      { "quit",   no_argument,      0, 'q' },
      { "receive",   no_argument,      0, 'r' },
      { "verbose",   no_argument,      0, 'v' },
      { "version",   no_argument,      0, VERSION_OPTION},
      { "dlc",   required_argument,   0, 'd' },
      { "identifier",   required_argument,   0, 'i' },
      { "loop",   required_argument,   0, 'l' },
      { 0,      0,         0, 0},
   };

   while ((opt = getopt_long(argc, argv, "hpqrvi:l:", long_options, NULL)) != -1) {
      switch (opt) {
         case 'h':
            print_usage(basename(argv[0]));
            exit(EXIT_SUCCESS);
            break;

         case 'p':
            use_poll = 1;
            break;

         case 'q':
            quit = 1;
            break;

         case 'r':
            receive = 1;
            break;

         case 'v':
            verbose++;
            break;

         case VERSION_OPTION:
            printf("flexraysequence %s\n", VERSION);
            exit(EXIT_SUCCESS);
            break;

         case 'l':
            if (optarg) {
               loopcount = strtoul(optarg, NULL, 0);
               infinite = 0;
            } else
               infinite = 1;
            break;

         case 'i':
            filter->flexray_id = strtoul(optarg, NULL, 0);
            break;

         case 'd':
            frame.frhead.plr = strtoul(optarg, NULL, 0);
            if((frame.frhead.plr < 1) || (frame.frhead.plr > MAX_FLEXRAY_DLC)) {
               printf("bad value flexray_dlc=%d\n", frame.frhead.plr);
               exit(EXIT_FAILURE);
            }
            break;


         default:
            fprintf(stderr, "Unknown option %c\n", opt);
            break;
      }
   }

   if (argv[optind] != NULL)
      interface = argv[optind];

   printf("interface = %s, family = %d, type = %d, proto = %d\n",
      interface, family, type, proto);

   frame.frhead.fid = filter->flexray_id;

   s = socket(family, type, proto);
   if (s < 0) {
      perror("socket");
      return 1;
   }

   addr.flexray_family = family;
   strncpy(ifr.ifr_name, interface, sizeof(ifr.ifr_name));
   if (ioctl(s, SIOCGIFINDEX, &ifr)) {
      perror("ioctl");
      return 1;
   }
   addr.flexray_ifindex = ifr.ifr_ifindex;

#if 0
   /* first don't recv. any msgs */
   if (setsockopt(s, SOL_FLEXRAY_RAW, FLEXRAY_RAW_FILTER, NULL, 0)) {
      perror("setsockopt");
      exit(EXIT_FAILURE);
   }
#endif
   
   if (bind(s, (struct sockaddr *)&addr, sizeof(addr)) < 0) {
      perror("bind");
      return 1;
   }
   
   memset(frame.data, 0, sizeof(frame.data));
   
   if (receive) {
      /* enable recv. now */
#if 0
      if (setsockopt(s, SOL_FLEXRAY_RAW, FLEXRAY_RAW_FILTER, filter, sizeof(filter))) {
         perror("setsockopt");
         exit(EXIT_FAILURE);
      }
#endif
      while ((infinite || loopcount--) && running) {   
         nbytes = read(s, &frame, sizeof(struct flexray_frame));
         if (nbytes < 0) {
            perror("read");
            return 1;
         }

         if (sequence_init) {
            sequence_init = 0;
            sequence = frame.data[0];
         }

         if (verbose > 1)
            printf("received frame. sequence number: %d\n", frame.data[0]);

         if (frame.data[0] != sequence) {
            printf("received wrong sequence count. expected: %d, got: %d\n",
               sequence, frame.data[0]);
            if (quit) {
               exit_value = EXIT_FAILURE;
               break;
            }
            sequence = frame.data[0];
         }

         sequence++;
         if (verbose && !sequence)
            printf("sequence wrap around (%d)\n", seq_wrap++);

      }
   } else {
      struct flexray_frame framefd;
      memset(&framefd, 0, sizeof(struct flexray_frame));
      
      framefd.frhead.fid = frame.frhead.fid;
      framefd.frhead.plr = frame.frhead.plr; // 0-127
      framefd.frhead.crc = 0x55;
      framefd.crc[0] = 0x01;
      framefd.crc[1] = 0x02;
      framefd.crc[2] = 0x03;

      printf("Send frames with frame_id=%X len=%d flexray_frame=%d\n", frame.frhead.fid, frame.frhead.plr, sizeof(struct flexray_frame));

      while ((infinite || loopcount--) && running) {
         struct timeval tvnow;
         ssize_t len;

         if (verbose > 1)
            printf("sending frame. sequence number: %d\n", sequence);

      again:
         gettimeofday(&tvnow, NULL);
         framefd.timestamp = (((unsigned long long)tvnow.tv_sec))*1000000ULL + tvnow.tv_usec;
         
         len = write(s, &framefd, sizeof(struct flexray_frame));
         if (len == -1) {
            switch (errno) {
               case ENOBUFS: {
                  int err;
                  struct pollfd fds[] = {
                     {
                        .fd   = s,
                        .events   = POLLOUT,
                     },
                  };

                  if (!use_poll) {
                     perror("write");
                     exit(EXIT_FAILURE);
                  }

                  err = poll(fds, 1, 1000);
                  if (err == -1 && errno != -EINTR) {
                     perror("poll()");
                     exit(EXIT_FAILURE);
                  }
               }
               case EINTR:   /* fallthrough */
                  goto again;
               default:
                  perror("write");
                  exit(EXIT_FAILURE);
            }
         }

         framefd.data[0]++;
         framefd.data[framefd.frhead.plr*2-1]++;
         framefd.frhead.rcc++;
         sequence++;

         if (verbose && !sequence)
            printf("sequence wrap around (%d)\n", seq_wrap++);
      }
   }

   exit(exit_value);
}
