# Provides common settings among MX-4 products that use Colibri VF61 COM

include conf/machine/include/imx-base.inc
include conf/machine/include/arm/armv7a/tune-cortexa5.inc

MACHINEOVERRIDES =. "vf:vf50:vf60:vfcv61:"

LINUX_VERSION_use-head-next = "6.1"
PREFERRED_PROVIDER_virtual/kernel ?= "linux-hostmobility"
KERNEL_DEFCONFIG = "imx_v6_v7_defconfig"
KERNEL_IMAGETYPE = "zImage"

# We store kernel on seperate MTD partition so no need to deploy to rootfs.
RDEPENDS:${KERNEL_PACKAGE_NAME}-base = ""

PREFERRED_PROVIDER_u-boot ?= "u-boot-hostmobility"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-hostmobility"
#PREFERRED_PROVIDER_u-boot-fw-utils ?= "u-boot-hostmobility-fw-utils"

# U-Boot NAND binary includes 0x400 padding required for NAND boot
UBOOT_BINARY ?= "u-boot-nand.imx"
UBOOT_MAKE_TARGET = "u-boot-nand.imx"

#disable build uboot for now.
#EXTRA_IMAGEDEPENDS += "u-boot"

IMAGE_FSTYPES = "tar.xz ubifs"

# Enable free --space-fixup (-F) by default, this allows DFU updates
MKUBIFS_ARGS = " -c 8112 -e 124KiB -m 2KiB -F"
UBINIZE_ARGS = " -p 128KiB -m 2048 -s 2048"
UBI_VOLNAME = "rootfs"

SERIAL_CONSOLE ?= "115200 ttyLP0"


PREFERRED_PROVIDER_j1708-lib = "j1708-lib_1.0.6"
PREFERRED_PROVIDER_j1708-test = "j1708-test_1.0.6"

MACHINE_EXTRA_RDEPENDS += " \
    kernel-modules \
    packagegroup-hostmobility-commercial \
    ${@bb.utils.contains('MACHINE_FEATURES', 'wifi', \
                          'linux-firmware-ath9k linux-firmware-wl18xx hostapd', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'flash', \
                          'mtd-utils mtd-utils-ubifs mtd-utils-misc mtd-utils-jffs2', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'alsa', \
                          'alsa-utils', '', d)} \
    libubootenv \
"

CORE_IMAGE_EXTRA_INSTALL += " \
    kernel-module-pic \
"


BBMASK += "recipes-bsp/u-boot/u-boot-hostmobility_2019.04.bb"

BBMASK +=  "recipes-multimedia/gstreamer/"
BBMASK +=  "recipes-core/fs-init/fs-init.bbappend"

BBMASK:imx-mainline-bsp = "meta-imx/"
