require recipes-kernel/linux/linux-imx.inc

SUMMARY = "Linux kernel for MX-4 products using Toradex Colibri VFxx COMs"

SRC_URI = "git://github.com/hostmobility/linux-toradex.git;protocol=https;branch=${SRCBRANCH} \
           file://defconfig"

KERNEL_MODULE_AUTOLOAD += "${@bb.utils.contains('COMBINED_FEATURES', 'usbgadget', ' libcomposite', '',d)}"

LOCALVERSION = "-${SRCBRANCH}"
SRCBRANCH = "mx4-bsp-2.0.x-vybrid"
SRCREV = "33fac6437474670595aaeca085e4fcbf87a8f665"
DEPENDS += "lzop-native bc-native u-boot-mkimage-native"
COMPATIBLE_MACHINE = "(mx4-c61|mx4-v61)"

# We use CONFIG_ARM_APPENDED_DTB=y and below shall take care of that

do_deploy_append() {
    cd ${B}
    cat ${KERNEL_OUTPUT_DIR}/zImage ${KERNEL_OUTPUT_DIR}/dts/${KERNEL_DEVICETREE} > combined-image
    mkimage -A arm -C none -a ${UBOOT_ENTRYPOINT} -e ${UBOOT_ENTRYPOINT} -T kernel -d combined-image ${KERNEL_OUTPUT_DIR}/uImage
    cd -
    
    type=uImage
    base_name=${type}-${KERNEL_IMAGE_BASE_NAME}
    install -m 0644 ${KERNEL_OUTPUT_DIR}/${type} ${DEPLOYDIR}/${base_name}.bin

    symlink_name=uImage-${KERNEL_IMAGE_SYMLINK_NAME}
    ln -sf ${base_name}.bin ${DEPLOYDIR}/${symlink_name}.bin
    ln -sf ${base_name}.bin ${DEPLOYDIR}/${type}
}
