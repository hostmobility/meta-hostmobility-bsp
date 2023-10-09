FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI += "\
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
"

# TODO fix rest of CAN
UNUSED_PATCHES = "\
file://0020-HMX-backport-m_can-from-linux-can-next.patch  \
file://0021-HMX-Add-suspend-to-backported-m_can-driver.patch \
file://0026-HMX-m_can-do-not-init-registers-until-bus-on.patch \
file://0027-tcan4x5x-add-direct-register-read-debug.patch \
file://0028-tcan4x4-stop-queue-first-in-transmit-to-avoid-race.patch \
file://0029-tcan4x5x-remove-msg-lost-netdev_err-print.patch \
"
SRC_URI += "file://defconfig"


SRCBRANCH = "lf-6.1.y_var02"
SRCREV = "56f4b40c3e548fb066f45b0a8c28196091644949"
LINUX_VERSION = "6.1.22"

SRCBRANCH:imx8mn-var-som= "lf-6.1.y_var02"
SRCREV:imx8mn-var-som = "9ac32222b830c4c72717c928a7b9d8792e22d26e"
LINUX_VERSION:imx8mn-var-som = "6.1.22"

SRCBRANCH:imx8mp-var-dart = "lf-6.1.y_var02"
SRCREV:imx8mp-var-dart = "592ac9656210927a8bbe4823ee7b30aaf191f7db"
LINUX_VERSION:imx8mp-var-dart = "6.1.22"

COMPATIBLE_MACHINE = "(imx8mp-var-dart-hmx1)"
