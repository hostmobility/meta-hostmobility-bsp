FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI += "\
    file://defconfig \
    file://0001-add-hmm-device-tree.patch \
"
SRCREV_meta-toradex-bsp = "f874412b7190aee21a4ca21c1ad21be4f9ffdd48"
SRCREV_meta-toradex-bsp:use-head-next = "${AUTOREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"
