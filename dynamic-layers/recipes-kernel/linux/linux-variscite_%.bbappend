FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI += "\
file://0001-host-watchdog-driver-v25.patch \
file://0002-temp-custom-board-dts.patch \
file://0001-add-hmx-device-tree.patch \
file://0002-Set-I2C1-for-imx8mp-var-dart.dtsi-to-lower-clock-fre.patch \
file://0001-TCAN114x-driver-with-normal-standby-sleep-mode.patch \
file://0018-power-reset-gpio-poweroff-add-force-mode.patch  \
file://0022-gpio-keys-make-disabled-keys-not-wake-system.patch  \
file://0030-gpio-pca953x-driver-minimize-error-print-out.patch \
file://0001-Add-Kinetic-KTD2026-2027-LED-driver.patch \
file://0034-tcan4x5x-Backport-linuxcan-next-6.6-driver.patch \
file://0035-tcan4x5x-remove-msg-lost-in-rxf0-netdev_err.patch \
file://0036-m_can-dont-enable-transceiver-when-probing.patch \
file://0037-HMX-tcan4x5x-enable-wakeup-when-interface-is-up.patch \
"
SRC_URI += "file://defconfig"


SRCBRANCH = "lf-6.1.y_var03"
SRCREV = "a6ba3adc78a578bfaf3bc005062077fd9e0e1b49"
LINUX_VERSION = "6.1.36"


COMPATIBLE_MACHINE = "(imx8mp-var-dart|imx8mp-var-dart-hmx1)"
