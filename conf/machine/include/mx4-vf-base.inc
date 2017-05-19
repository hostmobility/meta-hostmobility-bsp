# Provides common settings among MX-4 products that use Colibri VF61 COM

include conf/machine/include/imx-base.inc
include conf/machine/include/tune-cortexa5.inc

MACHINEOVERRIDES =. "vf:vf50:vf60:"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-hostmobility-vf"
KERNEL_IMAGETYPE = "zImage"

# We store kernel on seperate MTD partition so no need to deploy to rootfs.
RDEPENDS_kernel-base = ""

PREFERRED_PROVIDER_u-boot ?= "u-boot-hostmobility"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-hostmobility"
PREFERRED_PROVIDER_u-boot-fw-utils ?= "u-boot-hostmobility-fw-utils"

# U-Boot NAND binary includes 0x400 padding required for NAND boot
UBOOT_BINARY ?= "u-boot-nand.imx"
UBOOT_MAKE_TARGET = "u-boot-nand.imx"

IMAGE_FSTYPES = "sdcard.gz tar.xz ubifs"

# Enable free --space-fixup (-F) by default, this allows DFU updates
MKUBIFS_ARGS = " -c 8112 -e 124KiB -m 2KiB -F"
UBINIZE_ARGS = " -p 128KiB -m 2048 -s 2048"
UBI_VOLNAME = "rootfs"

SERIAL_CONSOLE ?= "115200 ttyLP0"

