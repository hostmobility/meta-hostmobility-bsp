FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI:append = " \
file://defconfig \
file://0001-add-hmx-device-tree.patch \
file://0001-Add-Kinetic-KTD2026-2027-LED-driver.patch \
file://0002-Set-I2C1-for-imx8mp-var-dart.dtsi-to-lower-clock-fre.patch \
file://0003-tcan4x5x-backport-m_can-driver-from-kernel-6.12.20.patch \
file://0004-tcan114x-add-driver-with-normal-standby-sleep-mode.patch \
file://0005-marvell-88q2xxx-with-correct-initialization.patch \
file://0018-power-reset-gpio-poweroff-add-force-mode.patch  \
file://0022-gpio-keys-make-disabled-keys-not-wake-system.patch  \
file://0030-gpio-pca953x-driver-minimize-error-print-out.patch \
"

SRCBRANCH = "6.6-2.2.x-imx_var01"
SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH}"
SRCREV = "76c877bc7a86d41fd65a22a20d410c8b83582c0a"

LINUX_VERSION = "6.6.138"
LINUX_VERSION_EXTENSION = "-var-lts-next"

COMPATIBLE_MACHINE = "(imx8mp-var-dart-hmx1)"
