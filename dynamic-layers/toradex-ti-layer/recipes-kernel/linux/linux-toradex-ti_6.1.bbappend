FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI += "\
    file://defconfig \
    file://0001-add-hmm-device-tree.patch \
"

SRCBRANCH = "toradex_ti-linux-6.1.y"
SRCREV_machine = "603f75dc931d81221f3d70f2a2fa08739add00d5"
SRCREV_machine:use-head-next = "${AUTOREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"
