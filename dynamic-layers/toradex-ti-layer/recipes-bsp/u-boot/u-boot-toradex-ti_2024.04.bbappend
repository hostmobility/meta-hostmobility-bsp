FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRCREV = "3eafd228d8e0c958a7aa166bb171b57db5fe43cd"
SRCREV:use-head-next = "${AUTOREV}"
SRCBRANCH = "toradex_ti-u-boot-2024.04"


SRC_URI += "\
	file://0001-adjust-verdin-am62_a53_defconfig.patch \
	file://0002-Add-HMM-custom-eeprom-read.patch \
	file://0003-HMM-customization.patch \
	file://0004-Use-dev-board-dtsi-for-hmm-with-modifications.patch \
"