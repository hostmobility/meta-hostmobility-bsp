
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
	file://0001-Add-hmx1-device-tree-for-u-boot.patch \
	file://0002-Add-hmx-eeprom-read.patch \
	file://0003-HMX-custom-imx8mp_var_dart.patch \
	file://0004-adjust-imx8mp_var_dart_defconfig-to-hmx.patch \
"
SRCBRANCH = "lf_v2024.04_6.6.52-2.2.0_var01"
SRCREV = "16e046f1dbf65360deb1fff230b76a752aff3f22"

COMPATIBLE_MACHINE = "(imx8mp-var-dart-hmx1)"
