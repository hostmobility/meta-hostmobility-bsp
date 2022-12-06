
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-mobility-imx:"

DRIVERBRANCH = "kirkstone"
SRCREV_FORMAT = "linuxkernel_hmcommercial"
SRCREV_hmcommercial = "${AUTOREV}"

SRC_URI:append:mx5-pt= " \
    file://0006-Add-device-tree-for-mx5-pt.patch \
    file://0007-imx6qdl-mx-v-hdmi-display-dtsi-file-for-mainline-linux.patch \
    git://git@github.com/hostmobility/hm-commercial.git;name=hmcommercial;branch=${DRIVERBRANCH};subpath=drivers;protocol=ssh;destsuffix=git/drivers \
"

do_patch:append() {
    # Add our drivers to the Makefiles to build them.
    echo 'obj-$(CONFIG_GPIO_NCV7751) += ncv7751-gpio-driver/gpio-ncv7751.o' >> '${S}/drivers/gpio/Makefile'
    echo 'obj-$(CONFIG_MX5_GPIO_OVERLAY) += gpio-overlay/gpio-overlay.o' >> '${S}/drivers/gpio/Makefile'
    echo 'obj-$(CONFIG_MX5_MODEM_DRIVER) += modem_controller/modem_controller.o' >> '${S}/drivers/gpio/Makefile'
    echo 'obj-$(CONFIG_MX5_COCPU) += mx5pt_cocpu_driver/' >> '${S}/drivers/platform/Makefile'

    echo 'source "drivers/platform/mx5pt_cocpu_driver/Kconfig"' >>'${S}/drivers/platform/Kconfig'
    echo 'source "drivers/gpio/gpio-overlay/Kconfig"' >>'${S}/drivers/gpio/Kconfig'
    echo 'source "drivers/gpio/ncv7751-gpio-driver/Kconfig"' >>'${S}/drivers/gpio/Kconfig'
    echo 'source "drivers/gpio/modem_controller/Kconfig"' >>'${S}/drivers/gpio/Kconfig'
}

PREFERRED_PROVIDER:virtual/bootloader = "u-boot-hostmobility"