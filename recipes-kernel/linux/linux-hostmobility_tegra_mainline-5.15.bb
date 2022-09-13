SUMMARY = "Linux Kernel for Host Mobility products based on Toradex Tegra COMs"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-hostmobility-mainline-4.19:"

inherit kernel siteinfo
include conf/tdx_version.conf

LINUX_VERSION ?= "5.15.67"

LOCALVERSION = "-${PR}"

GENERIC_PATCHES = " \
"
MACHINE_PATCHES = " \
    file://defconfig \
    file://0001-Add-device-tree-files-for-t30.patch \
    file://0002-include-t30-dtb-in-makefile.patch \
    file://0003-Add-mx4_pic-and-can-xcvr-to-spidev.patch \
    file://0005-Add-support-for-flexray-device-driver.patch \
    file://0007-Add-flexray-and-pps-generator-for-5.15.67-kernel.patch \
"


#SRC_URI = " \
#    https://cdn.kernel.org/pub/linux/kernel/v5.x/linux-${PV}.tar.xz \
#    ${GENERIC_PATCHES} \
#    ${MACHINE_PATCHES} \
#"
#SRC_URI[md5sum] = "025c07e37cb89afa2b26bb4850ce7fa2"
#SRC_URI[sha256sum] = "c981abe15c4b9496cdadb04e4f4afb88b3a78f31ced8d07684ee9c58b3fadb64"

# For CI use one could use the following instead (plus patches still of course)

SRCREV = "${AUTOREV}"
PV = "${LINUX_VERSION}+git${SRCPV}"
S = "${WORKDIR}/git"
SRCBRANCH = "linux-5.15.y"
SRC_URI= " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;protocol=git;branch=${SRCBRANCH} \
    ${GENERIC_PATCHES} \
    ${MACHINE_PATCHES} \
"


COMPATIBLE_MACHINE = "(mx4-t30)"
KERNEL_EXTRA_ARGS = " LOADADDR=0x80008000 "

# One possibiltiy for changes to the defconfig:
config_script () {
    echo "dummy" > /dev/null
}

KCONFIG_MODE="--alldefconfig"

KBUILD_DEFCONFIG ?= "${KERNEL_DEFCONFIG}"

do_uboot_mkimage:prepend() {
    cd ${B}
}

