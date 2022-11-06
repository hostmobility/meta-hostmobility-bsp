# Provides common settings among MX-5 products that use imx6

MACHINEOVERRIDES =. "mx6:mx6q:mx6dl:mxv-base:"

IMX_DEFAULT_BSP = "nxp"

include conf/machine/include/imx-base.inc
require conf/machine/include/arm/armv7a/tune-cortexa9.inc

KERNEL_IMAGETYPE = "zImage"
PREFERRED_PROVIDER_virtual/kernel = "linux-mobility-imx"

# We store kernel on seperate MTD partition so no need to deploy to rootfs.
RDEPENDS:${KERNEL_PACKAGE_NAME}-base = ""

PREFERRED_PROVIDER_u-boot = "u-boot-hostmobility"
PREFERRED_PROVIDER_virtual/bootloader = "u-boot-hostmobility"
PREFERRED_PROVIDER_u-boot-fw-utils ?= "libubootenv"
PREFERRED_PROVIDER_j1708-lib = "j1708-lib_2.0.0"
PREFERRED_PROVIDER_j1708-test = "j1708-test_2.0.0"

UBOOT_BINARY ?= "u-boot-dtb.${UBOOT_SUFFIX}"
UBOOT_MAKE_TARGET = "all"

IMAGE_FSTYPES = "wic.gz tar.xz"
# wic support
WKS_FILE = "hm-mx5-uboot.wks.in"
WIC_CREATE_EXTRA_ARGS:append = " --no-fstab-update"

UBOOT_ENTRYPOINT = "0x82008000"
UBOOT_LOADADDRESS = "${UBOOT_ENTRYPOINT}"

SERIAL_CONSOLE ?= "115200 ttyLP0"

MACHINE_EXTRA_RDEPENDS:append = " \
    kernel-modules \
    e2fsprogs-resize2fs \
    packagegroup-hostmobility-commercial-mx5 \
    ${@bb.utils.contains('MACHINE_FEATURES', 'wifi', \
                          'linux-firmware-sd8887 linux-firmware-sd8897 hostapd', '', d)} \
    cryptodev-module \
    cryptodev-tests \
    libubootenv \
"

KERNEL_MODULE_AUTOLOAD += "cryptodev"
# fix u-boot-fw-utils \ using libubootenv if we need fw_setenv?

#BBMASK += "recipes-kernel/kernel-modules/kernel-module-imx-gpu-viv"
