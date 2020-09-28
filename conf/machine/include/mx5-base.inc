# Provides common settings among MX-5 products that use imx6

MACHINEOVERRIDES =. "mx6:mx6q:mx6dl:"

IMX_DEFAULT_BSP = "nxp"

require conf/machine/include/imx-base.inc
require conf/machine/include/tune-cortexa9.inc

PREFERRED_PROVIDER_virtual/kernel = "linux-hostmobility"
KERNEL_IMAGETYPE = "zImage"

# We store kernel on seperate MTD partition so no need to deploy to rootfs.
RDEPENDS_${KERNEL_PACKAGE_NAME}-base = ""

PREFERRED_PROVIDER_u-boot = "u-boot-hostmobility"
PREFERRED_PROVIDER_virtual/bootloader = "u-boot-hostmobility"
PREFERRED_PROVIDER_u-boot-fw-utils ?= "libubootenv"

UBOOT_BINARY ?= "u-boot-dtb.imx"
UBOOT_MAKE_TARGET = "u-boot-dtb.imx"

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
    alsa-utils \
    ${@bb.utils.contains('LICENSE_FLAGS_WHITELIST', 'closed', \
        'packagegroup-hostmobility-commercial-mx5', '', d)} \
"

KERNEL_MODULE_AUTOLOAD += "cryptodev-module"
# fix u-boot-fw-utils \ using libubootenv if we need fw_setenv?


BBMASK +=  "recipes-angstrom/angstrom/angstrom-feed-configs.bbappend"
BBMASK +=  "recipes-core/fs-init/fs-init.bbappend"
BBMASK +=  "recipes-core/usb-rndis-systemd/usb-rndis-systemd.bbappend"


