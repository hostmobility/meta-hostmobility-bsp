FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI += "\
file://0001-add-hmx-device-tree.patch \
file://0002-Set-I2C1-for-imx8mp-var-dart.dtsi-to-lower-clock-fre.patch \
file://0001-TCAN114x-driver-with-normal-standby-sleep-mode.patch \
file://0009-Fix-tcan114x_mode_store-for-sleep-standby.patch \
file://0016-improve-tcan4x5x-spi-performance.-coalesce-irqs.patch \
file://0017-Add-suspend-to-optimized-tcan4x5x-driver.patch \
file://0018-power-reset-gpio-poweroff-add-force-mode.patch  \
"
# file://0011-Add-control-for-selective-wakeup.patch
# file://0008-Add-suspend-to-tcan4x5x-driver.patch 

SRC_URI += "file://defconfig"

SRCBRANCH = "5.15-2.0.x-imx_var01"
KERNEL_SRC ?= "git://github.com/varigit/linux-imx;protocol=https"
SRCREV = "dd055f07f727803a7542541f560f69daed8209f4"
LINUX_VERSION = "5.15.50"
