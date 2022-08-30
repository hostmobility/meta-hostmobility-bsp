env set i2c_bus 2
env set i2c_dev 0x46
env set update_wanted_i2c_register 0x0002
env set usbdev 0
env set usbpart 1
env set wic_file mx5-image.wic.gz

# 600000 milliseconds = 10 minutes timeout for programming 
FLASH_TIMEOUT=600000
mw.l ${loadaddr} ${FLASH_TIMEOUT}
CURRENT_STATE_TIMEOUT_REG=0x4001.2
if i2c write ${loadaddr} ${i2c_dev} ${CURRENT_STATE_TIMEOUT_REG} 4 -s ; then echo Watchdog set to 10 minutes; else echo WARNING: FAILED TO SET CO-CPU WATCHDOG, FLASHING MAY BE INTERRUPTED IF CO-CPU HAS BOOT TIMEOUT ENABLED!; fi

# Start USB device and try to load wic_file into memory if load fails, exit the script (and boot normally).
env set loadimage_usb "usb start; fatload usb ${usbdev}:${usbpart} ${loadaddr} ${wic_file}"
echo Loading ${wic_file} from USB ${usbdev}:${usbpart};
if run loadimage_usb; then echo load ok; else echo load failed, aborting; exit; fi

#write the image onto the mmc. if it fails exit the script (and boot normally). TODO add a retry? TODO check checksum
env set writeimage "gzwrite mmc ${mmcdev} ${loadaddr} 0x${filesize} 100000 0"
echo Extracting ${wic_file} to mmc ${mmcdev};

if run writeimage; then echo write ok; else echo write failed, aborting; exit; fi

echo Resetting update flags;
# Write a zero  to the update_wanted i2c register(.2 means 16 bit wide register)
i2c mw ${i2c_dev} ${update_wanted_i2c_register}.2 0 1 || echo 'FLASH MX5_SCRIPT ERROR: Could not reset update flag'

