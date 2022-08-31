/*
 * linux/flexray.h
 *
 * Definitions for FlexRay network layer (socket addr / FlexRay frame)
 *
 */

#ifndef FLEXRAY_H
#define FLEXRAY_H

#include <linux/types.h>
#include <linux/socket.h>

/* FlexRay kernel definitions */

/* special address description flags for the FLEXRAY_ID */
#define FLEXRAY_PPI_FLAG	0x40 /* Payload preamble indicator */
#define FLEXRAY_NFI_FLAG	0x20 /* Null frame indikator */
#define FLEXRAY_SYN_FLAG	0x10 /* Sync frame indikator */
#define FLEXRAY_SFI_FLAG	0x08 /* Startup frame indikator */

/* define Flexray Type for Filtering of Frame ID:s */
typedef __u64 flexray_frame_filter_t;

/* Flexray header with additional information (flags).
   Please note that the header is not an identical
   representation of the original 5-byte header! */
typedef struct {
	__u32	flags;  /* Frame and error flags */
	__u16	fid;	/* Frame id/slot id */
	__u8	plr;	/* 0-254 bytes in data[] area */
	__u16	crc;	/* original FlexRay header CRC */
	__u8	rcc;	/* FlexRay received cycle count */
} flexray_header_t;

/**
 * struct flexray_frame - basic FlexRay frame structure
 * @head:      Header
 * @data:      the FlexRay frame payload.
 * @crc:       the FlexRay frame CRC
 */

/* FlexRay frame with associated metadata */
struct flexray_frame {
	__u32	seq;
	__u64	timestamp;  /* timeval or timespec */
	flexray_header_t frhead; /* slightly modified FR header */
	__u8	data[254] __attribute__((aligned(8)));  //
	__u8	crc[3];     /* Use TBI */
};

typedef struct flexray_frame flexray_frame_t;

/* particular protocols of the protocol family PF_FLEXRAY */
#define FLEXRAY_RAW      1 /* RAW sockets */
#define FLEXRAY_NPROTO   2
#if 0
#define FLEXRAY_BCM      2 /* Broadcast Manager */
#define FLEXRAY_TP16  3 /* VAG Transport Protocol v1.6 */
#define FLEXRAY_TP20  4 /* VAG Transport Protocol v2.0 */
#define FLEXRAY_MCNET 5 /* Bosch MCNet */
#define FLEXRAY_ISOTP 6 /* ISO 15765-2 Transport Protocol */
#define FLEXRAY_NPROTO   7
#endif
#define SOL_FLEXRAY_BASE 110

struct sockaddr_flexray {
	sa_family_t	flexray_family;
	int		flexray_ifindex;
};

struct flexray_filter {
	flexray_frame_filter_t flexray_id;
	flexray_frame_filter_t flexray_mask;
};

#endif /* FLEXRAY_H */
