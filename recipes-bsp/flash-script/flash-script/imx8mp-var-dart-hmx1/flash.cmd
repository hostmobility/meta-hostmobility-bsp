env set usbdev 0
env set usbpart 1
env set wic_file hmx-image.wic.gz
env set set_red_led "i2c mw 0x30 7.1 0x50 1;i2c mw 0x30 4.1 0x4 1"

#set white led flashing faster.
env set white_blink_fast "i2c mw 0x30 1.1 1 1;"
run set_white_flashing_led; 
run white_blink_fast;

# TODO set watchdog (wdt start) 600000 milliseconds = 10 minutes timeout for programming

# Start USB device and try to load wic_file into memory if load fails, exit the script (and boot normally).
if test -n "$usb_flash_wanted"; then 
    env set loadimage_usb "load usb ${usbdev} ${img_addr} ${wic_file}"
    echo Loading ${wic_file} from USB ${usbdev};
    if run loadimage_usb; then 
        echo load ok; 
    else 
        run set_red_led
        echo load failed, aborting;
        sleep 10;
        reset;  
    fi
    #delete usb_flash_wanted (should not be needed but it makes the installation run once)
    env delete usb_flash_wanted
else
    #/boot flash way
    # runs loadbootscript(this file) than calls bootscript after exit
    env set wic_file /boot/hmx-image.wic.gz
    env set loadimage "load mmc ${mmcdev}:${mmcpart} ${img_addr} ${wic_file}"
    echo Loading ${wic_file} from mmc ${mmcdev}:${mmcpart};
    if run loadimage; then 
        echo load ok; 
    else 
        run set_red_led
        echo load failed, aborting;
        sleep 10;
        reset; 
    fi
fi
#write the image onto the mmc. if it fails exit the script (and boot normally). TODO add a retry? TODO check checksum
env set writeimage "gzwrite mmc ${mmcdev} ${img_addr} 0x${filesize} 100000 0"
echo Extracting ${wic_file} to mmc ${mmcdev};

if run writeimage; then 
    echo write ok;
    run set_blue_led;
    reset;
else
    run set_red_led
    echo write failed, aborting;
    sleep 10;
    reset;
fi
