# Copyright (C) 2020 Host Mobility AB

SUMMARY = "Host Mobility BSP Linux kernel for MX-5 products"

inherit kernel-yocto kernel
require recipes-kernel/linux/linux-yocto.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

DEPENDS += "lzop-native bc-native"

LINUX_VERSION ?= "4.19.35"
LINUX_VERSION_EXTENSION:append = "-imx"

SRC_URI = "\
    git://source.codeaurora.org/external/imx/linux-imx;name=linuxkernel;branch=${LINUXBRANCH};protocol=ssh;nocheckout=1 \
    file://defconfig \    
    git://git@github.com/hostmobility/hm-commercial.git;name=hmcommercial;subpath=drivers;protocol=ssh;destsuffix=git/drivers \
    file://0001-Compiler-Attributes-add-support-for-__copy-gcc-9.patch \
    file://0002-include-linux-module.h-copy-__init-__exit-attrs-to-i.patch \
    file://0001-perf-Make-perf-able-to-build-with-latest-libbfd.patch \
    file://0001-Add-regulator-node-name-for-imx6qdl.dtsi.patch \
    file://0002-Add-support-on-USB-for-EG25-modem.patch \
    file://0003-Add-stability-fix-for-tlv320aic3x-codec-driver.patch \
    file://0004-Add-imx6qp-mx5.dtb-to-makfile.patch \
    file://0006-Add-device-tree-for-mx5-pt.patch \
    file://0007-Remove-dma-on-spi-HC-Ignore-dma-for-now-use-pio.patch \
"

LINUXBRANCH = "imx_4.19.35_1.0.0"

SRCREV_FORMAT = "linuxkernel_hmcommercial"

SRCREV_linuxkernel = "e4452f4458e4272345aa773e726107cb74ef2974"
SRCREV_hmcommercial = "6bb9cce9e762b44872966e2d65e403e213058131"

PV = "${LINUX_VERSION}+git${SRCPV}"

KCONFIG_MODE="--alldefconfig"

COMPATIBLE_MACHINE = "mx5-pt"
KBUILD_DEFCONFIG ?= "${KERNEL_DEFCONFIG}"


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
