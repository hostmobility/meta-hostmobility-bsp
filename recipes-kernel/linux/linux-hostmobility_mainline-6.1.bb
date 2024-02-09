SUMMARY = "Linux Kernel for Host Mobility products based on Toradex Tegra COMs"
SECTION = "kernel"
LICENSE = "GPL-3.0-or-later"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-mobility-imx:"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-hostmobility-mainline-6.1:"

inherit kernel siteinfo


COMPATIBLE_MACHINE = "(mx4-t30|vfcv61|)"

LINUX_VERSION ?= "6.1.26"

LOCALVERSION = "-${PR}"

PV = "${LINUX_VERSION}"
S = "${WORKDIR}/linux-${PV}"

GENERIC_PATCHES = " \
    file://0003-Add-mx4_pic-and-can-xcvr-to-spidev.patch \
"

MACHINE_PATCHES:tegra3 = " \
    file://defconfig \
    file://0001-Add-pps-generator-gpio-for-flexray.patch \
    file://0002-Add-support-for-flexray-device-driver.patch \
    file://0004-T30-Update-device-tree-with-better-suspend-routine.patch \
    file://0005-To-get-the-SD-MMC-host-device-ID-read-the-alias-from.patch \
    file://0009-Change-asix-driver-to-version-4.23.patch \
    file://0010-fix-missing-operating-points-v2-for-mmc-4-and-2.patch \
"

MACHINE_PATCHES:vfcv61 = " \
    file://0001-add-device-tree-for-c61-v61.patch \
    file://0002-Add-support-on-USB-for-EG25-modem.patch \
    file://vfcv61_delta_imx_v6_v7_defconfig.cfg \
"

MACHINE_PATCHES:mx4-c61-rio = " \
    file://0001-add-device-tree-for-c61-v61.patch \
    file://0005-add-serdev-node-to-rs485.patch \
    file://0002-Add-support-on-USB-for-EG25-modem.patch \
    file://vfcv61_delta_imx_v6_v7_defconfig.cfg \
    file://serdev.cfg \
"

DELTA_KERNEL_DEFCONFIG:vfcv61 = "vfcv61_delta_imx_v6_v7_defconfig.cfg"

SRC_URI = " \
    https://cdn.kernel.org/pub/linux/kernel/v6.x/linux-${PV}.tar.xz \
    ${GENERIC_PATCHES} \
    ${MACHINE_PATCHES} \
"
SRC_URI[md5sum] = "fbebee762efa2914323432854518a91b"
SRC_URI[sha256sum] = "dfdcc143a879d64a5ee99213b2b4b05b5dccd566c144df93bca1e204df64c110"

# For CI use one could use the following instead (plus patches still of course)
LINUX_VERSION_use-head-next ?= "6.1"
SRCREV_use-head-next = "ca48fc16c49388400eddd6c6614593ebf7c7726a"
PV_use-head-next = "${LINUX_VERSION}+git${SRCPV}"
S_use-head-next = "${WORKDIR}/git"
SRCBRANCH_use-head-next = "linux-6.1.y"
SRC_URI_use-head-next = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;protocol=git;branch=${SRCBRANCH} \
    ${GENERIC_PATCHES} \
    ${MACHINE_PATCHES} \
"


KERNEL_EXTRA_ARGS = " LOADADDR=0x80008000 "

KCONFIG_MODE="--alldefconfig"

KBUILD_DEFCONFIG ?= "${KERNEL_DEFCONFIG}"

do_uboot_mkimage:prepend() {
    cd ${B}
}

#For Vf (mx4) only
KERNEL_MODULE_AUTOLOAD:vf60 += "${@bb.utils.contains('COMBINED_FEATURES', 'usbgadget', ' libcomposite', '',d)}"
# use an old way to add device tree to the end of the uImage for C61. changing uboot from 2015 than change to load dtb seperate
do_deploy:append:vf60() {
    cd ${B}
    cat ${KERNEL_OUTPUT_DIR}/uImage ${KERNEL_OUTPUT_DIR}/dts/${KERNEL_DEVICETREE} > combined-image
    cp ${KERNEL_OUTPUT_DIR}/uImage ${KERNEL_OUTPUT_DIR}/only_uImage
    cd -

    type=uImage
    base_name=${type}-${KERNEL_IMAGETYPE}
    install -m 0644 ${B}/combined-image ${DEPLOYDIR}/${base_name}.bin

    symlink_name=uImage-${KERNEL_IMAGE_SYMLINK_NAME}
    ln -sf ${base_name}.bin ${DEPLOYDIR}/${symlink_name}.bin
    ln -sf ${base_name}.bin ${DEPLOYDIR}/${type}
}