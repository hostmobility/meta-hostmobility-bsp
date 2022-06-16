inherit kernel siteinfo
SUMMARY = "Linux Kernel for Host Mobility products based on Toradex Tegra COMs"
SECTION = "kernel"
LICENSE = "GPLv2"

LINUX_VERSION ?= "4.14.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PV = "${LINUX_VERSION}+gitr${SRCREV}"

DEPENDS += "lzop-native bc-native u-boot-mkimage-native"

S = "${WORKDIR}/git"

LOCALVERSION = "test"
SRC_URI = "git://github.com/hostmobility/linux-toradex.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "hm_tegra_4.14"
SRCREV = "eefc00c4f143ea76efed7d53215ea492d588450b"
KERNEL_EXTRA_ARGS = "LOADADDR=0x00408000 "

COMPATIBLE_MACHINE = "(mx4-mil)"

# One possibiltiy for changes to the defconfig:
config_script () {
#    #example change to the .config
#    #sets CONFIG_TEGRA_CAMERA unconditionally to 'y'
#    sed -i -e /CONFIG_TEGRA_CAMERA/d ${B}/.config
#    echo "CONFIG_TEGRA_CAMERA=y" >> ${B}/.config
    echo "dummy" > /dev/null
}

do_configure_prepend () {

    cd ${S}
    export KBUILD_OUTPUT=${B}

    install -m 0644 ${THISDIR}/linux-hostmobility/${KERNEL_DEFCONFIG} ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} || die "No default configuration for ${MACHINE} / ${KERNEL_DEFCONFIG} available."

    oe_runmake ${KERNEL_DEFCONFIG}

    #maybe change some configuration
    config_script

    #Add Host Mobility BSP Version as LOCALVERSION
    sed -i -e /CONFIG_LOCALVERSION/d ${B}/.config
    echo "CONFIG_LOCALVERSION=\"${LOCALVERSION}\"" >> ${B}/.config

    #Add GIT revision to the local version
    head=`git --git-dir=${S}/.git rev-parse --verify --short HEAD 2> /dev/null`
    printf "%s%s" +g $head > ${S}/.scmversion

    cd - > /dev/null
}

do_uboot_mkimage_prepend () {
    cd ${B}
}

KCONFIG_MODE="--alldefconfig"

KBUILD_DEFCONFIG ?= "${KERNEL_DEFCONFIG}"

do_deploy_append() {
    cd ${B}
    cat ${KERNEL_OUTPUT_DIR}/zImage ${KERNEL_OUTPUT_DIR}/dts/${KERNEL_DEVICETREE} > combined-image
    mkimage -A arm -C none -a ${UBOOT_ENTRYPOINT} -e ${UBOOT_ENTRYPOINT} -T kernel -d combined-image ${KERNEL_OUTPUT_DIR}/uImage
    cd -
    
    type=uImage
    base_name=${type}-${KERNEL_IMAGETYPE}_base
    install -m 0644 ${KERNEL_OUTPUT_DIR}/${type} ${DEPLOYDIR}/${base_name}.bin

    symlink_name=uImage-${KERNEL_IMAGE_SYMLINK_NAME}
    ln -sf ${base_name}.bin ${DEPLOYDIR}/${symlink_name}.bin
    ln -sf ${base_name}.bin ${DEPLOYDIR}/${type}
}