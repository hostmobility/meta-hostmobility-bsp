FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRCREV = "ffbbc1dd68f41724f3a885ed18cffe3e064e9134"
SRCREV:use-head-next = "${AUTOREV}"
SRCBRANCH = "toradex_ti-u-boot-2024.04"


SRC_URI += "\
	file://0001-adjust-verdin-am62_a53_defconfig.patch \
"