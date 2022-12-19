# Provides common Tegra3 SoC settings on MX-4 products

MACHINEOVERRIDES =. "tegra:tegra3:tegra3mainline:"
MACHINEOVERRIDES =. "use-head-next:"

# meta-angstrom/conf/distro/include/arm-defaults.inc
require conf/machine/include/soc-family.inc

DEFAULTTUNE = "armv7athf-neon"
require conf/machine/include/tune-cortexa9.inc

#disable build uboot for now.
#EXTRA_IMAGEDEPENDS += "u-boot"

PREFERRED_PROVIDER_virtual/kernel = "linux-hostmobility"

KERNEL_IMAGETYPE = "uImage"

# The kernel lives in a seperate FAT or UBI partition, don't deploy it in /boot
RDEPENDS_kernel-base = ""

PREFERRED_PROVIDER_u-boot = "u-boot-hostmobility"
PREFERRED_PROVIDER_virtual/bootloader = "u-boot-hostmobility"
PREFERRED_PROVIDER_u-boot-fw-utils ?= "u-boot-hostmobility-fw-utils"

# used by sysvinit_2
SERIAL_CONSOLE = "115200 ttyS0"

IMAGE_FSTYPES ?= "ext3"
# Set ext3 partition arguments -b 4096 -g 32768 to allow faster reflashing of the system when using t30_hmupdate file in /boot on target machine.
EXTRA_IMAGECMD_ext3 = "-b 4096 -g 32768 "

BBMASK += "recipes-bsp/u-boot/u-boot-hostmobility_2019.04.bb"

# wic support
#WKS_FILE = "sdimage-bootpart-mx4-t30.wks"
#WIC_CREATE_EXTRA_ARGS_append = " --no-fstab-update"

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