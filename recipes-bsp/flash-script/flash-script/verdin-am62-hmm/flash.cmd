env set usbdev 0
env set usbpart 1
env set wic_file hmm-image.wic.gz

# TODO set watchdog (wdt start) 600000 milliseconds = 10 minutes timeout for programming

# Start USB device and try to load wic_file into memory if load fails, exit the script (and boot normally).

#Set LEDs 1 to 4 off.
led green:led_1 off
led green:led_2 off
led green:led_3 off
led green:led_4 off

led red:led_1 off
led red:led_2 off
led red:led_3 off
led red:led_4 off

env set loadimage_usb "load usb ${usbdev} ${ramdisk_addr_r} ${wic_file}"
echo Loading ${wic_file} from USB ${usbdev};
if run loadimage_usb; then
    echo load ok;
    led green:led_3 on
else
    echo load failed, aborting;
    sleep 10;
    led red:led_3 on
    reset;
fi

#write the image onto the mmc. if it fails exit the script (and boot normally). TODO add a retry? TODO check checksum
env set writeimage "gzwrite mmc 0 ${ramdisk_addr_r} 0x${filesize} 100000 0"
echo Extracting ${wic_file} to mmc 0;

if run writeimage; then
    echo write ok, continue booting up the new system (no new uboot env!);
    led green:led_4 on
else
    echo write failed, aborting;
    sleep 10;
    led red:led_4 on
    reset;
fi
