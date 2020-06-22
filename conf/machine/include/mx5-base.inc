# Provides common settings among MX-5 products that use imx6

MACHINEOVERRIDES =. "mx6:mx6q:mx6dl:"

IMX_DEFAULT_BSP = "nxp"

require conf/machine/include/imx-base.inc
require conf/machine/include/tune-cortexa9.inc

PREFERRED_PROVIDER_virtual/kernel = "linux-hostmobility"
KERNEL_IMAGETYPE = "zImage"

# We store kernel on seperate MTD partition so no need to deploy to rootfs.
RDEPENDS_kernel-base = ""

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
    usb-rndis-systemd \
    usb-suspend-resume \
    e2fsprogs-resize2fs \
    fs-init \
"

# TODO add packagegroup-hostmobility-commercial or mx5 commercial tools.
# ${@bb.utils.contains('LICENSE_FLAGS_WHITELIST', 'closed', \
#                          'packagegroup-hostmobility-commercial', '', d)} \
# fix u-boot-fw-utils \ using libubootenv if we need fw_setenv?