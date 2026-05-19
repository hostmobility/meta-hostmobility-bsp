
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-mobility-imx:"

KBRANCH = "6.6-2.2.x-imx"
SRC_URI = "git://github.com/Freescale/linux-fslc.git;branch=${KBRANCH};protocol=https"
SRCREV = "5ff4cf4d61e11f0fdf8d4e2e54fbb203e46d34b2"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "6.6.74"

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

KBUILD_DEFCONFIG:mx6-generic-bsp = "imx_v7_defconfig"


# Local version indicates the branch name in the NXP kernel tree where patches are collected from.
LOCALVERSION = "-lf-6.6.y"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"