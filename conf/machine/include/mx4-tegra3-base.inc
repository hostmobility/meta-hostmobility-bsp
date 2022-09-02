# Provides common Tegra3 SoC settings on MX-4 products

MACHINEOVERRIDES =. "tegra:tegra3:tegra3mainline:"

# meta-angstrom/conf/distro/include/arm-defaults.inc
require conf/machine/include/soc-family.inc

DEFAULTTUNE = "armv7athf-neon"
require conf/machine/include/arm/armv7a/tune-cortexa9.inc

EXTRA_IMAGEDEPENDS += "u-boot"

PREFERRED_PROVIDER_virtual/kernel = "linux-hostmobility"

KERNEL_IMAGETYPE = "uImage"

# The kernel lives in a seperate FAT or UBI partition, don't deploy it in /boot
RDEPENDS:kernel-base = ""

PREFERRED_PROVIDER_u-boot = "u-boot-hostmobility"
PREFERRED_PROVIDER_virtual/bootloader = "u-boot-hostmobility"
PREFERRED_PROVIDER_u-boot-fw-utils ?= "u-boot-hostmobility-fw-utils"

# used by sysvinit_2
SERIAL_CONSOLE = "115200 ttyS0"

IMAGE_FSTYPES ?= "tar.xz"

BBMASK += "recipes-bsp/u-boot/u-boot-hostmobility_2019.04.bb"

IMAGE_FSTYPES = "tar.xz"
# wic support
#WKS_FILE = "sdimage-bootpart-mx4-t30.wks"
#WIC_CREATE_EXTRA_ARGS:append = " --no-fstab-update"

IMAGE_BOOT_FILES ?= " \
    ${KERNEL_IMAGETYPE} \
    ${KERNEL_DEVICETREE} \
"
WKS_FILE_DEPENDS ?= " \
   virtual/bootloader \
    \
    e2fsprogs-native \
    bmap-tools-native \
"

PREFERRED_PROVIDER_j1708-lib = "j1708-lib_1.0.6"
PREFERRED_PROVIDER_j1708-test = "j1708-test_1.0.6"

MACHINE_EXTRA_RDEPENDS += " \
    u-boot-hostmobility-fw-utils \
    ${@bb.utils.contains('LICENSE_FLAGS_WHITELIST', 'closed', \
                          'packagegroup-hostmobility-commercial', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'wifi', \
                          'linux-firmware-ath9k linux-firmware-wl18xx hostapd', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'flash', \
                          'mtd-utils mtd-utils-ubifs mtd-utils-misc mtd-utils-jffs2', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'alsa', \
                          'alsa-utils', '', d)} \
    e2fsprogs-resize2fs \
    fs-init \
    packagegroup-hostmobility-flexray-utils \
"


CORE_IMAGE_EXTRA_INSTALL += " \
    kernel-module-pic \
"