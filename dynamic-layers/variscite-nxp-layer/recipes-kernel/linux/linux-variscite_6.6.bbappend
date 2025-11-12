FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI:append = " \
file://defconfig \
file://0001-add-hmx-device-tree.patch \
file://0002-Set-I2C1-for-imx8mp-var-dart.dtsi-to-lower-clock-fre.patch \
file://0001-Add-Kinetic-KTD2026-2027-LED-driver.patch \
file://0001-TCAN114x-driver-with-normal-standby-sleep-mode.patch \
file://0018-power-reset-gpio-poweroff-add-force-mode.patch  \
file://0022-gpio-keys-make-disabled-keys-not-wake-system.patch  \
file://0030-gpio-pca953x-driver-minimize-error-print-out.patch \
file://0035-tcan4x5x-remove-msg-lost-in-rxf0-netdev_err.patch \
"

#TODO check if this patches is needed:
#0036-m_can-dont-enable-transceiver-when-probing.patch
#0037-HMX-tcan4x5x-enable-wakeup-when-interface-is-up.patch
#0011 patch is work in progress does not build.

SRCBRANCH = "lf-6.6.y_6.6.52-2.2.0_var01"
SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH}"
SRCREV = "bf1b1781e47b1388faaf8689bd7296b3a82b8316"

LINUX_VERSION = "6.6.52"
LINUX_VERSION_EXTENSION = "-var-lts-next"

COMPATIBLE_MACHINE = "(imx8mp-var-dart-hmx1)"
