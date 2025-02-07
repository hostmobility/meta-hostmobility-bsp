FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI:append = "\
    file://defconfig \
    file://0001-add-hmm-device-tree.patch \
"

PV = "${LINUX_VERSION}+git${SRCPV}"
