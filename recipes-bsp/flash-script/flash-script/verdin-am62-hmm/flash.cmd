env set usbdev 0
env set usbpart 1
env set wic_file hmm-image.wic.gz
env set wic_file_sha256 ${wic_file}.sha256

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

env set hash_addr_old 0x83000000;
env set hash_addr_new 0x84000000;

env set loadimage_usb "load usb ${usbdev} ${ramdisk_addr_r} ${wic_file}"

echo Loading ${wic_file} from USB ${usbdev};
if run loadimage_usb; then
    echo load ok;
    led green:led_3 on
    # keep the filesize for later flash it will be altered by next commands before gzwrite
    env set loadimage_filesize ${filesize}
    # set hash of the new wic file.
    hash sha256 ${ramdisk_addr_r} ${filesize} *${hash_addr_new};

    # try to load on bootfs (/boot) the old hashed file if no continue flash.

    if fatload mmc 0:1 ${hash_addr_old} ${wic_file_sha256}; then
        echo "Found existing ${wic_file_sha256}, verifying..."
        cmp.b ${hash_addr_old} ${hash_addr_new} 32

        if test $? -eq 0; then
            echo "file hash matches, no need to flash."
            exit;
        else
            echo "Hash mismatch! Flashing..."
        fi
    else
        echo "No existing ${wic_file_sha256} found. Flashing..."
    fi

else
    echo load failed, aborting;
    sleep 10;
    led red:led_3 on
    reset;
fi

#write the image onto the emmc. if it fails exit the script (and boot normally). TODO add a retry? TODO check checksum

echo "Extracting ${wic_file} to mmc 0 with increase buffer of 1MB(100000)";

if gzwrite mmc 0 ${ramdisk_addr_r} 0x${loadimage_filesize} 100000 0; then
    echo "write ok, create new hash file";
    led green:led_4 on
    fatwrite mmc 0:1 ${hash_addr_new} ${wic_file_sha256} 32;
    echo "write ok, continue booting up the new system (no new uboot env!)";
    sleep 1;
    reset;
else
    echo write failed, aborting;
    led red:led_4 on
    sleep 10;
    reset;
fi

