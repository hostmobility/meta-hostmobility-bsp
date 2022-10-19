FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI += "\
file://0001-add-hmx-device-tree.patch \
file://0001-TCAN114x-driver-with-normal-standby-sleep-mode.patch \
file://0004-HMX-DT-update-for-ethernet-on-USB.patch \
file://0005-HMX-DT-update-for-USB3-and-USB-regulator.patch \
"
SRC_URI += "file://defconfig"

