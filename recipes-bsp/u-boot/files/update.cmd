# i2c variables TODO this is realtime clock, change to co-cpu. TODO Need rewrite when co-cpu i2c protocol is ready. 
env set i2c_bus 2
env set i2c_dev 0x51
env set i2c_addr_1 0x08
env set i2c_addr_2 0x09

env set usbdev 0
env set usbpart 1
env set reflash_image mx5-image.wic.gz
env set bool_true 1
env set bool_false 0

#Create 2 memory addresses to store i2c read values in, reflash_1, reflash_2
#And zero those 2 memory addresser 

setexpr reflash_1 ${loadaddr} + 4
setexpr reflash_2 ${reflash_1} + 4
mw ${reflash_1} 0 1
mw ${reflash_2} 0 1

#Set loadaddr to bool true. To use cmp, memory compare, later.
mw ${loadaddr} ${bool_true} 1

#set i2c bus and read 2 flags into memory.
i2c dev ${i2c_bus}
i2c read ${i2c_dev} ${i2c_addr_1} 0x01 ${reflash_1}
i2c read ${i2c_dev} ${i2c_addr_2} 0x01 ${reflash_2}

#load the compare memory statement into env variables for readability. 
env set cmp_flag_1 "cmp ${loadaddr} ${reflash_1} 1"
env set cmp_flag_2 "cmp ${loadaddr} ${reflash_2} 1"

#check if flag reflash_1 or reflash_2 is equal to bool_true, if not, exit the script (and boot normally).
if run cmp_flag_1; then echo Update flag 1 detected, trying to update..; else if run cmp_flag_2; then echo Update flag 2 detected, trying to update..; else echo No updateflag detected, booting normaly; exit;fi;fi;

#start USB device and try to load reflash_image into memory if load fails, exit the scipt (and boot normaly).
env set loadimage_usb "usb start; fatload usb ${usbdev}:${usbpart} ${loadaddr} ${reflash_image}"
echo Loading ${reflash_image} from USB ${usbdev}:${usbpart};
if run loadimage_usb; then echo load ok; else echo load failed, aborting; exit; fi

#write the image onto the mmc. if it fails exit the script (and boot normally). TODO add a retry? TODO check checksum
env set writeimage "gzwrite mmc ${mmcdev} ${loadaddr} 0x${filesize} 100000 0"
echo Extracting ${reflash_image} to mmc ${mmcdev};
if run writeimage; then echo write ok; else echo write failed, aborting; exit; fi

#reset i2c flags to bool_false when successful. 
env set reset_i2c_flags "i2c mw ${i2c_dev} ${i2c_addr_1} ${bool_false}; i2c mw ${i2c_dev} ${i2c_addr_2} ${bool_false};"
echo Resetting update flags;
if run reset_i2c_flags; then echo reset i2c flags ok; echo Update successfull!;else echo i2c reset flags failed.; exit; fi;
