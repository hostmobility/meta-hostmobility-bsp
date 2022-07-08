FILESEXTRAPATHS_prepend := "${THISDIR}/linux-variscite:"
SRC_URI += "file://0001-add-hmx-device-tree.patch"

KERNEL_DEFCONFIG_imx8mp-var-dart-hmx0 = "imx8_var_hmx0_defconfig"
KERNEL_DEFCONFIG_imx8mp-var-dart-hmx1 = "imx8_var_hmx1_defconfig"
