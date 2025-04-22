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
    file://0006-Add-device-tree-for-mx5-pt.patch \
    file://0007-imx6qdl-mx-v-hdmi-display-dtsi-file-for-mainline-linux.patch \
"

SRC_URI = "git://github.com/Freescale/linux-fslc.git;name=linuxkernel;branch=${LINUXBRANCH};protocol=https;nocheckout=1  \
    file://defconfig \  
    ${GENERIC_PATCHES} \
    ${MACHINE_PATCHES} \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

PV = "5.15.86+git${SRCPV}"

LINUXBRANCH = "5.15.x+fslc"
SRCREV_linuxkernel = "a86fe5cb4fe96228e358108d0ed91d2c49bce097"
SRCREV = "${SRCREV_linuxkernel}"

LOCALVERSION = ""


