SUMMARY = "Linux Kernel for Host Mobility products based on Toradex Tegra COMs"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mobility-imx:"
FILESEXTRAPATHS_prepend := "${THISDIR}/linux-hostmobility-mainline-4.19:"

inherit kernel siteinfo

COMPATIBLE_MACHINE = "(mx4-t30|vfcv61|mx4-hostcom)"

LINUX_VERSION ?= "4.19.66"

LOCALVERSION = "-${PR}"

PV = "${LINUX_VERSION}"
S = "${WORKDIR}/linux-${PV}"

GENERIC_PATCHES = " \
    file://0001-Update-rtc-pcf85063-from-upstream.patch \
"
MACHINE_PATCHES_tegra3 = " \
    file://defconfig \
    file://0001-Add-device-tree-files-for-t30.patch \
    file://0003-Add-mx4_pic-and-can-xcvr-to-spidev.patch \
    file://0004-Add-support-for-thermal-throttling.patch \
    file://0005-Add-support-for-flexray-device-driver.patch \
    file://0006-Add-flexray-driver-support.patch \
    file://0007-Add-pps-generator-gpio.patch \
    file://0008-mmc-read-mmc-alias-from-device-tree.patch \
    file://0009-Change-asix-driver-to-version-4.23.patch \
"

MACHINE_PATCHES_vfcv61 = " \
    file://0001-add-device-tree-for-c61-v61.patch \
    file://0002-Add-support-on-USB-for-EG25-modem.patch \
    file://defconfig \
"

SRC_URI = " \
    https://cdn.kernel.org/pub/linux/kernel/v4.x/linux-${PV}.tar.xz \
    ${GENERIC_PATCHES} \
    ${MACHINE_PATCHES} \
"
SRC_URI[md5sum] = "025c07e37cb89afa2b26bb4850ce7fa2"
SRC_URI[sha256sum] = "c981abe15c4b9496cdadb04e4f4afb88b3a78f31ced8d07684ee9c58b3fadb64"

# For CI use one could use the following instead (plus patches still of course)
LINUX_VERSION_use-head-next ?= "4.19"
SRCREV_use-head-next = "1845fbffe0c5fb49ad29d18c5d75f0c165977de0"
PV_use-head-next = "${LINUX_VERSION}+git${SRCPV}"
S_use-head-next = "${WORKDIR}/git"
SRCBRANCH_use-head-next = "linux-4.19.y"
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
