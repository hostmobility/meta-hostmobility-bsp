# Provides common settings among MX-5 products that use imx6

MACHINEOVERRIDES =. "mx6:mx6q:mx6dl:"

IMX_DEFAULT_BSP = "nxp"

require conf/machine/include/imx-base.inc
require conf/machine/include/tune-cortexa9.inc

KERNEL_IMAGETYPE = "zImage"
PREFERRED_PROVIDER_virtual/kernel = "linux-mobility-imx"

# We store kernel on seperate MTD partition so no need to deploy to rootfs.
RDEPENDS_${KERNEL_PACKAGE_NAME}-base = ""

PREFERRED_PROVIDER_u-boot = "u-boot-hostmobility"
PREFERRED_PROVIDER_virtual/bootloader = "u-boot-hostmobility"
PREFERRED_PROVIDER_u-boot-fw-utils ?= "libubootenv"

UBOOT_BINARY = "u-boot-dtb.imx"
UBOOT_MAKE_TARGET = "all"

IMAGE_FSTYPES = "wic.gz tar.xz"
# wic support
WKS_FILE = "hm-mx5-uboot.wks.in"
WIC_CREATE_EXTRA_ARGS_append = " --no-fstab-update"

UBOOT_ENTRYPOINT = "0x82008000"
UBOOT_LOADADDRESS = "${UBOOT_ENTRYPOINT}"

SERIAL_CONSOLE ?= "115200 ttyLP0"

MACHINE_EXTRA_RDEPENDS_append = " \
    kernel-modules \
    e2fsprogs-resize2fs \
    packagegroup-hostmobility-commercial-mx5 \
    ${@bb.utils.contains('MACHINE_FEATURES', 'wifi', \
                          'linux-firmware-sd8897 hostapd', '', d)} \
"

KERNEL_MODULE_AUTOLOAD += "cryptodev-module"
# fix u-boot-fw-utils \ using libubootenv if we need fw_setenv?


BBMASK +=  "recipes-core/fs-init/fs-init.bbappend"
BBMASK +=  "recipes-core/usb-rndis-systemd/usb-rndis-systemd.bbappend"
BBMASK += "recipes-connectivity/iproute2/iproute2_3.15.0.bb"
