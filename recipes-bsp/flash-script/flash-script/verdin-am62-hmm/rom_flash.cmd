#write the image onto the mmc already loaded by uuu tools.
env set writeimage "gzwrite mmc 0 ${ramdisk_addr_r} 0x${filesize} 100000 0"
echo Extracting ${wic_file} to mmc 0;

if run writeimage; then
    echo write ok;
    env set flash_image_status "ok"
else
    echo write failed, aborting;
    env set flash_image_status "write failed, aborting"
fi
