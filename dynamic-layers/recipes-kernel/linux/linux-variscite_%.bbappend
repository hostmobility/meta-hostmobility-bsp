FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI += "\
file://0001-add-hmx-device-tree.patch \
file://0002-Set-I2C1-for-imx8mp-var-dart.dtsi-to-lower-clock-fre.patch \
file://0001-TCAN114x-driver-with-normal-standby-sleep-mode.patch \
file://0018-power-reset-gpio-poweroff-add-force-mode.patch  \
file://0022-gpio-keys-make-disabled-keys-not-wake-system.patch  \
file://0030-pca953x-driver-minimize-error-print-out.patch \
file://0034-tcan4x5x-Backport-linuxcan-next-6.6-driver.patch \
file://0035-tcan4x5x-remove-msg-lost-in-rxf0-netdev_err.patch \
"

UNUSED_PATCHES = "\
file://0020-HMX-backport-m_can-from-linux-can-next.patch  \
file://0021-HMX-Add-suspend-to-backported-m_can-driver.patch \
file://0026-HMX-m_can-do-not-init-registers-until-bus-on.patch \
file://0027-tcan4x5x-add-direct-register-read-debug.patch \
file://0028-tcan4x4-stop-queue-first-in-transmit-to-avoid-race.patch \
file://0029-tcan4x5x-remove-msg-lost-netdev_err-print.patch \
"

SRC_URI += "file://defconfig"

SRCBRANCH = "5.15-2.0.x-imx_var01"
KERNEL_SRC ?= "git://github.com/varigit/linux-imx;protocol=https"
SRCREV = "dd055f07f727803a7542541f560f69daed8209f4"
LINUX_VERSION = "5.15.50"

COMPATIBLE_MACHINE = "(imx8mp-var-dart-hmx1)"
