FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI += "\
    file://defconfig \
    file://0001-add-hmm-device-tree.patch \
"
SRCBRANCH = "toradex_ti-linux-6.6.y"
SRCREV_machine = "f874412b7190aee21a4ca21c1ad21be4f9ffdd48"
SRCREV_machine:use-head-next = "${AUTOREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"
