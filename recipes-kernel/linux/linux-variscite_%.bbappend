FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI += "file://0001-add-hmx-device-tree.patch"
SRC_URI += "file://defconfig"

