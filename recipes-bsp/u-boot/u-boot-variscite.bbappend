FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
	file://0001-Add-hmx1-device-tree-for-u-boot.patch \
	file://0002-Add-hmx-eeprom-read.patch \
	file://0003-HMX-custom-imx8mp_var_dart.patch \
	file://0004-adjust-imx8mp_var_dart_defconfig-to-hmx.patch \
"
#Keep this version untill we can step up u-boot hm patches.
SRCBRANCH = "lf_v2022.04_var01"
SRCREV = "09207fe3fd0110748280dcff64e3fd6d2e0a4b3b"
