FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI += "\
    file://defconfig \
    file://0001-add-hmm-device-tree.patch \
    file://0002-iio-st-sensors-retry-whoami.patch \
"
SRCBRANCH = "toradex_ti-linux-6.6.y"
SRCREV_machine:use-head-next = "${AUTOREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"
