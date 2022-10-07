FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI += "\
file://0001-add-hmx-device-tree.patch \
file://0001-TCAN114x-driver-with-normal-standby-sleep-mode.patch \
"
SRC_URI += "file://defconfig"

