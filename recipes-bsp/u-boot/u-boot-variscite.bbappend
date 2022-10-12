FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
	file://0001-Add-hmx1-device-tree-for-u-boot.patch \
	file://0001-Set-Linux-DT-file-to-hmx1-if-u-boot-detects-legacy.patch \
	"
