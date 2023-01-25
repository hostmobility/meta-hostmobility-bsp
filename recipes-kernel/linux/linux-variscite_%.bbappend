FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI += "\
file://0001-add-hmx-device-tree.patch \
file://0001-TCAN114x-driver-with-normal-standby-sleep-mode.patch \
file://0004-HMX-DT-update-for-ethernet-on-USB.patch \
file://0005-HMX-DT-update-for-USB3-and-USB-regulator.patch \
file://0008-Add-suspend-to-tcan4x5x-driver.patch \
file://0009-Fix-tcan114x_mode_store-for-sleep-standby.patch \
file://0010-HMX-DT-Set-wakeup-source-on-flexcan.patch \
file://0012-HMX-DT-Setup-T1-phys.patch \
file://0013-HMX-DT-USB-controllers-are-swapped-on-rev-4A.patch \
file://0014-HMX-DT-fixed-T1-phy-100Mbps-workaround.patch   \
"
# file://0011-Add-control-for-selective-wakeup.patch

SRC_URI += "file://defconfig"

