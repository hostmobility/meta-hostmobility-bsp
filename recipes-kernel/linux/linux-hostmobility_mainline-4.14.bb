SUMMARY = "Linux Kernel for Host Mobility products based on Toradex Tegra COMs"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-toradex-mainline-4.14:"

inherit kernel siteinfo
include conf/tdx_version.conf

LINUX_VERSION ?= "4.19.63"

LOCALVERSION = "-${PR}"
PR = "${TDX_VER_ITEM}"

PV = "${LINUX_VERSION}"
S = "${WORKDIR}/linux-${PV}"

GENERIC_PATCHES = " \
"
MACHINE_PATCHES = " \
    file://0001-Add-device-tree-files-for-t30.patch \
    file://0002-Add-defconfig-file-for-t30.patch \
    file://0003-Add-mx4_pic-and-can-xcvr-to-spidev.patch \
    file://0004-Add-support-for-thermal-throttling.patch \
    file://0005-Add-support-for-flexray-device-driver.patch \
    file://0006-Add-flexray-driver-support.patch \
    file://0008-mmc-read-mmc-alias-from-device-tree.patch \
"


SRC_URI = " \
    https://cdn.kernel.org/pub/linux/kernel/v4.x/linux-${PV}.tar.xz \
    ${GENERIC_PATCHES} \
    ${MACHINE_PATCHES} \
"
SRC_URI[md5sum] = "5f72b9a1e40192aecf867ff2cdcc15ba"
SRC_URI[sha256sum] = "75988760b931864b46292dcfad1dd54b3f4da10168041f48ca6d7f6dd4e5d25d"

# For CI use one could use the following instead (plus patches still of course)
LINUX_VERSION_use-head-next ?= "4.19"
SRCREV_use-head-next = "${AUTOREV}"
PV_use-head-next = "${LINUX_VERSION}+git${SRCPV}"
S_use-head-next = "${WORKDIR}/git"
SRCBRANCH_use-head-next = "linux-4.19.y"
SRC_URI_use-head-next = " \
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

do_configure_prepend () {
    cd ${S}
    export KBUILD_OUTPUT=${B}
    oe_runmake ${KERNEL_DEFCONFIG}

    #maybe change some configuration
    config_script

    #Add Toradex BSP Version as LOCALVERSION
    sed -i -e /CONFIG_LOCALVERSION/d ${B}/.config
    echo "CONFIG_LOCALVERSION=\"${LOCALVERSION}\"" >> ${B}/.config

    cd - > /dev/null
}

do_uboot_mkimage_prepend() {
    cd ${B}
}
