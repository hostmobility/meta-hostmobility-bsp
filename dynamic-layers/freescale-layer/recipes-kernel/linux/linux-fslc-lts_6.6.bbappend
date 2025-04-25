
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-mobility-imx:"

#KERNEL_SRC = "git://github.com/nxp-imx/linux-imx.git;protocol=https;branch=${SRCBRANCH};name=linuxkernel"

SRC_URI:append:mx5-pt= " \
    file://mxv_delta_imx_v7_defconfig.cfg \
    file://0006-Add-device-tree-for-mxv-pt.patch \
    file://0007-imx6qdl-mx-v-hdmi-display-dtsi-file-for-fslc-imx-linux.patch \
"

# Note defconfig file for Mxv-pt it will use sources/meta-freescale/recipes-kernel/linux/linux-imx/imx-nxp-bsp/defconfig
# which is identical with imx_v7_defconfig and we append our customisation with mxv_delta_imx_v7_defconfig 
# for mainline builds we use our defconfig under recipes-kernel/linux/linux-mobility-imx/mx5-pt/defconfig where mxv_delta_imx_v7_defconfig is already built in.

unset KBUILD_DEFCONFIG
DELTA_KERNEL_DEFCONFIG:mx5-pt = "mxv_delta_imx_v7_defconfig.cfg"

KBUILD_DEFCONFIG:mx6-generic-bsp = "imx_v6_v7_defconfig"