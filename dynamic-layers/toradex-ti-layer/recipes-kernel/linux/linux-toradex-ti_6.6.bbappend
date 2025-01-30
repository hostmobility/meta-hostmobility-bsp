FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI += "\
    file://defconfig \
    file://0001-add-hmm-device-tree.patch \
"
SRCREV_meta-toradex-bsp = "c6957acbaaf98de436802c52cc9ca059959f08c1"
SRCREV_meta-toradex-bsp:use-head-next = "${AUTOREV}"

PV = "${LINUX_VERSION}+git${SRCPV}"
