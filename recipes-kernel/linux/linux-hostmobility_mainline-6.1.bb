SUMMARY = "Linux Kernel for Host Mobility products based on Toradex Tegra COMs"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mobility-imx:"
FILESEXTRAPATHS_prepend := "${THISDIR}/linux-hostmobility-mainline-6.1:"

inherit kernel siteinfo

COMPATIBLE_MACHINE = "(mx4-t30|vfcv61|mx4-hostcom)"

LINUX_VERSION ?= "6.1.26"

LOCALVERSION = "-${PR}"

PV = "${LINUX_VERSION}"
S = "${WORKDIR}/linux-${PV}"

GENERIC_PATCHES = " \
"
MACHINE_PATCHES_tegra3 = " \
    file://defconfig \
    file://0001-Add-pps-generator-gpio-for-flexray.patch \
    file://0002-Add-support-for-flexray-device-driver.patch \
    file://0003-Add-mx4_pic-and-can-xcvr-to-spidev.patch \
    file://0004-T30-Update-device-tree-with-better-suspend-routine.patch \
    file://0005-To-get-the-SD-MMC-host-device-ID-read-the-alias-from.patch \
    file://0009-Change-asix-driver-to-version-4.23.patch \
"


SRC_URI = " \
    https://cdn.kernel.org/pub/linux/kernel/v6.x/linux-${PV}.tar.xz \
    ${GENERIC_PATCHES} \
    ${MACHINE_PATCHES} \
"
SRC_URI[md5sum] = "025c07e37cb89afa2b26bb4850ce7fa2"
SRC_URI[sha256sum] = "c981abe15c4b9496cdadb04e4f4afb88b3a78f31ced8d07684ee9c58b3fadb64"

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

# One possibiltiy for changes to the defconfig:
config_script () {
    echo "dummy" > /dev/null
}

KCONFIG_MODE="--alldefconfig"

KBUILD_DEFCONFIG ?= "${KERNEL_DEFCONFIG}"

do_uboot_mkimage_prepend() {
    cd ${B}
}

#For Vf (mx4)
KERNEL_MODULE_AUTOLOAD_vf60 += "${@bb.utils.contains('COMBINED_FEATURES', 'usbgadget', ' libcomposite', '',d)}"

DEPENDS_vf60 += "lzop-native bc-native u-boot-mkimage-native"


# We use CONFIG_ARM_APPENDED_DTB=y and below shall take care of that
do_deploy_append_vf60() {
    cd ${B}
    cat ${KERNEL_OUTPUT_DIR}/zImage ${KERNEL_OUTPUT_DIR}/dts/${KERNEL_DEVICETREE} > combined-image
    mkimage -A arm -C none -a ${UBOOT_ENTRYPOINT} -e ${UBOOT_ENTRYPOINT} -T kernel -d combined-image ${KERNEL_OUTPUT_DIR}/uImage
    cd -
    
    type=uImage
    base_name=${type}-${KERNEL_IMAGETYPE}
    install -m 0644 ${KERNEL_OUTPUT_DIR}/${type} ${DEPLOYDIR}/${base_name}.bin

    symlink_name=uImage-${KERNEL_IMAGE_SYMLINK_NAME}
    ln -sf ${base_name}.bin ${DEPLOYDIR}/${symlink_name}.bin
    ln -sf ${base_name}.bin ${DEPLOYDIR}/${type}
}
