
SUMMARY = "Linux Kernel for Host Mobility products based on Toradex Tegra COMs"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-mobility-imx:"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-hostmobility-mainline-4.19:"

inherit kernel siteinfo

COMPATIBLE_MACHINE = "(mx4-c61|mx4-hostcom)"
LINUX_VERSION ?= "4.19.270"

LOCALVERSION = "-${PR}"

PV = "${LINUX_VERSION}"
S = "${WORKDIR}/linux-${PV}"

GENERIC_PATCHES = " \
    file://0002-Add-support-on-USB-for-EG25-modem.patch \
    file://0003-Add-mx4_pic-and-can-xcvr-to-spidev.patch \
    file://0001-Update-rtc-pcf85063-from-upstream.patch \
"
MACHINE_PATCHES:mx4-hostcom = " \
    file://0001-add-hostcom-device-tree.patch \
    file://defconfig \
"

MACHINE_PATCHES:mx4-c61 = " \
    file://0001-add-device-tree-for-c61-v61.patch \
    file://defconfig \
"

SRC_URI = " \
    https://cdn.kernel.org/pub/linux/kernel/v4.x/linux-${PV}.tar.xz \
    ${GENERIC_PATCHES} \
    ${MACHINE_PATCHES} \
"
SRC_URI[md5sum] = "28019f0595ded76ba53d2f4790ce2f7e"
SRC_URI[sha256sum] = "2144843abc8e3ea2ae53bc1c76b73c033ac4eedb004125762a368fd3a60ed292"

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


KERNEL_EXTRA_ARGS = " LOADADDR=0x80008000 "

# One possibiltiy for changes to the defconfig:
config_script () {
    echo "dummy" > /dev/null
}

KCONFIG_MODE="--alldefconfig"

KBUILD_DEFCONFIG ?= "${KERNEL_DEFCONFIG}"

DEPENDS += "lzop-native bc-native u-boot-mkimage-native"

# We use CONFIG_ARM_APPENDED_DTB=y and below shall take care of that

do_deploy:append() {
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
