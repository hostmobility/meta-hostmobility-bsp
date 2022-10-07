require recipes-kernel/linux/linux-imx.inc

SUMMARY = "Host Mobility BSP Linux kernel for MX-5 products"

DEPENDS += "lzop-native bc-native"

KCONFIG_MODE="--alldefconfig"

COMPATIBLE_MACHINE = "mx5-pt"
KBUILD_DEFCONFIG ?= "${KERNEL_DEFCONFIG}"

GENERIC_PATCHES = " \
    file://0002-Add-support-on-USB-for-EG25-modem.patch \
"

GENERIC_PATCHES = ""

MACHINE_PATCHES:mx5-pt= " \
    git://git@github.com/hostmobility/hm-commercial.git;name=hmcommercial;branch=${DRIVERBRANCH};subpath=drivers;protocol=ssh;destsuffix=git/drivers \
    file://0006-Add-device-tree-for-mx5-pt.patch \
    file://0001-Revert-usbnet-smsc95xx-Fix-deadlock-on-runtime-resum.patch \
    file://0002-Revert-usbnet-smsc95xx-Forward-PHY-interrupts-to-PHY.patch \
"

SRC_URI = "git://github.com/Freescale/linux-fslc.git;name=linuxkernel;branch=${LINUXBRANCH};protocol=https;nocheckout=1  \
    file://defconfig \  
    ${GENERIC_PATCHES} \
    ${MACHINE_PATCHES} \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

PV = "5.15.63+git${SRCPV}"

LINUXBRANCH = "5.15.x+fslc"
SRCREV_linuxkernel = "577e58342355e7c3870948a6c3c2b5840cf694ea"

DRIVERBRANCH = "kirkstone"
SRCREV_FORMAT = "linuxkernel_hmcommercial"
SRCREV_hmcommercial = "${AUTOREV}"

LOCALVERSION = ""


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

