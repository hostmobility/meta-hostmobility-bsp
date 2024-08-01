env set usbdev 0
env set usbpart 1
env set wic_file hmm-image.wic.gz

# TODO set watchdog (wdt start) 600000 milliseconds = 10 minutes timeout for programming

# Start USB device and try to load wic_file into memory if load fails, exit the script (and boot normally).
if test -n "$usb_flash_wanted"; then
    env set loadimage_usb "load usb ${usbdev} ${ramdisk_addr_r} ${wic_file}"
    echo Loading ${wic_file} from USB ${usbdev};
    if run loadimage_usb; then
        echo load ok;
    else 
        echo load failed, aborting;
        sleep 10;
        reset;
    fi
    #delete usb_flash_wanted (should not be needed but it makes the installation run once)
    env delete usb_flash_wanted
else
    #/boot flash way
    # runs loadbootscript(this file) than calls bootscript after exit
    env set loadimage "load mmc 0:1 ${ramdisk_addr_r} /boot/${wic_file}"
    echo Loading /boot/${wic_file} from mmc 0:1;
    if run loadimage; then
        echo load ok;
    else
        echo load failed, aborting;
        sleep 10;
        boot;
    fi
fi
#write the image onto the mmc. if it fails exit the script (and boot normally). TODO add a retry? TODO check checksum
env set writeimage "gzwrite mmc 0 ${ramdisk_addr_r} 0x${filesize} 100000 0"
echo Extracting ${wic_file} to mmc 0;

if run writeimage; then
    echo write ok;
    reset;
else
    echo write failed, aborting;
    sleep 10;
    reset;
fi
