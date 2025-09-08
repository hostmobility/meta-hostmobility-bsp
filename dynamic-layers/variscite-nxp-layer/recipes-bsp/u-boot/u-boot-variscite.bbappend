
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
	file://0001-Add-hmx1-device-tree-for-u-boot.patch \
	file://0002-Add-hmx-eeprom-read.patch \
	file://0003-HMX-custom-imx8mp_var_dart.patch \
	file://0004-adjust-imx8mp_var_dart_defconfig-to-hmx.patch \
"
SRCBRANCH = "lf_v2024.04_6.6.52-2.2.0_var01"
SRCREV = "859f74afa1cebda40b58a96800899f3dc5acb30b"

COMPATIBLE_MACHINE = "(imx8mp-var-dart-hmx1)"
