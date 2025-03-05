#!/bin/bash
#  SPDX-License-Identifier: MIT
#
# This script allows flashing boot binaries for Host Monitor Mini (TI AM625).
# It must be located in the `/bootfs` alongside the files `tiboot3.bin`, `tispl.bin` and `u-boot.img`.
# By default the `boot0` area is selected. If you want to keep `boot0` and use `boot1` instead, run it with `BOOT_PART=1` and this area will be used after next reboot if flashing was successful.

set -e
set -u

# Default to mmcblk0boot0, allow override via command line argument
BOOT_PART="${1:-0}"  # 0 for mmcblk0boot0, 1 for mmcblk0boot1

if [[ "$BOOT_PART" == "0" ]]; then
    TARGET_BOOT_PARTITION="mmcblk0boot0"
elif [[ "$BOOT_PART" == "1" ]]; then
    TARGET_BOOT_PARTITION="mmcblk0boot1"
else
    echo "Invalid boot partition. Use 0 for mmcblk0boot0 or 1 for mmcblk0boot1."
    exit 1
fi

MMC_BOOT="/sys/block/${TARGET_BOOT_PARTITION}/force_ro"
DEVICE="/dev/${TARGET_BOOT_PARTITION}"
FILES=("tiboot3.bin" "tispl.bin" "u-boot.img")
OFFSETS=(0 1024 5120)

# Disable force_ro to allow writing
echo 0 > "$MMC_BOOT"

# Flash all binaries
for i in "${!FILES[@]}"; do
    if [[ -f "${FILES[$i]}" ]]; then
        echo "Flashing ${FILES[$i]} to ${DEVICE} at offset ${OFFSETS[$i]}"
        dd if="${FILES[$i]}" of="$DEVICE" bs=512 seek="${OFFSETS[$i]}" status=progress
    else
        echo "Error: ${FILES[$i]} not found!"
        exit 1
    fi
done

sync

if [[ "$BOOT_PART" == "0" ]]; then
    echo "Next boot will be mmcblk0boot0"
    mmc bootpart enable 1 1 /dev/mmcblk0
else
    echo "Next boot will be mmcblk0boot1"
    mmc bootpart enable 1 2 /dev/mmcblk0
fi

echo "Flashing boot binaries complete!"
exit 0