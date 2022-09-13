require recipes-kernel/linux/linux-imx.inc

DEPENDS += "lzop-native bc-native"

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-mobility-imx:"

COMPATIBLE_MACHINE = "(mx4-c61|mx4-hostcom)"

# Todo fix patch for  modem
#GENERIC_PATCHES = " \
#    file://0002-Add-support-on-USB-for-EG25-modem.patch \
#"

GENERIC_PATCHES = ""

MACHINE_PATCHES:mx4-hostcom = " \
    file://0001-add-hostcom-device-tree.patch \
    file://defconfig \
"

MACHINE_PATCHES:mx4-c61 = " \
    file://0001-add-device-tree-for-c61-v61.patch \
    file://defconfig \
"

SRC_URI = "git://github.com/Freescale/linux-fslc.git;branch=${SRCBRANCH};protocol=https  \
    ${GENERIC_PATCHES} \
    ${MACHINE_PATCHES} \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

PV = "5.15.1+git${SRCPV}"

SRCBRANCH = "5.15.x+fslc"
SRCREV = "b6abb62daa5511c4a3eaa30cbdb02544d1f10fa2"


SUMMARY = "Linux kernel for MX-4 'vf' products using fslc"

KERNEL_MODULE_AUTOLOAD += "${@bb.utils.contains('COMBINED_FEATURES', 'usbgadget', ' libcomposite', '',d)}"

LOCALVERSION = ""

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
