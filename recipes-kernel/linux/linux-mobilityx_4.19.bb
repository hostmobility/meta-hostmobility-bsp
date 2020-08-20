# Copyright (C) 2020 Host Mobility AB

SUMMARY = "Host Mobility BSP Linux kernel for MX-5 products"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

DEPENDS += "lzop-native bc-native"

LINUX_VERSION ?= "4.19.35"
LINUX_VERSION_EXTENSION_append = "-mobilityx"

SRC_URI = "\
    git://git@gitlab.com/hostmobility/linux-mx5;name=linuxkernel;branch=${LINUXBRANCH};protocol=ssh;nocheckout=1 \
    git://git@gitlab.com/hostmobility/gpio-overlay;name=gpiooverlay;protocol=ssh;destsuffix=git/extra-drivers/gpio-overlay \
    git://git@gitlab.com/hostmobility/l9826-gpio-driver;name=l9826;protocol=ssh;destsuffix=git/extra-drivers/l9826-gpio-driver \
    file://0001-Compiler-Attributes-add-support-for-__copy-gcc-9.patch \
    file://0002-include-linux-module.h-copy-__init-__exit-attrs-to-i.patch \
    file://0001-perf-Make-perf-able-to-build-with-latest-libbfd.patch \
"

LINUXBRANCH = "imx_4.19.35_1.0.0_mx5_bringup_prototype2"
SRCREV_linuxkernel = "3c825662e7dc2381d6b604429faec86886872ae2"
SRCREV_gpiooverlay = "${AUTOREV}" 
SRCREV_l9826 = "${AUTOREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"

KCONFIG_MODE="--alldefconfig"
do_configure_prepend () {
    cd ${S}
    export KBUILD_OUTPUT=${B}
    oe_runmake ${KERNEL_DEFCONFIG}
}

COMPATIBLE_MACHINE = "mx5-pt"
KBUILD_DEFCONFIG ?= "${KERNEL_DEFCONFIG}"

do_configure_append() {
    # Disable the built-in driver, this means that kernel-module-imx-gpu-viv
    # will be used instead which is provided by meta-freescale and built as
    # an out of tree module
    #
    # This makes the GPU driver independent of the Linux kernel version
    sed -i -e "/CONFIG_MXC_GPU_VIV[ =]/d" '${B}/.config'
    echo "# CONFIG_MXC_GPU_VIV is not set" >> '${B}/.config'
}

do_patch_append() {
    # Remove our drivers from gpio Makefile
    sed -i -e "/gpio-overlay/d" '${S}/drivers/gpio/Makefile'
    sed -i -e "/gpio-l9826/d" '${S}/drivers/gpio/Makefile'

    # Create links into kernel tree 
    ln -s ${S}/extra-drivers/gpio-overlay/gpio-overlay.c ${S}/drivers/gpio
    ln -s ${S}/extra-drivers/l9826-gpio-driver/gpio-l9826.c ${S}/drivers/gpio

    # Add our drivers to Makefile to build them
    # obj-m builds as module, obj-y includes in kernel image
    echo "obj-y += gpio-l9826.o" >> '${S}/drivers/gpio/Makefile'
    echo "obj-y += gpio-overlay.o" >> '${S}/drivers/gpio/Makefile'
}
