FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

unset KBUILD_DEFCONFIG

SRC_URI:append = "\
    file://defconfig \
"

PV = "${LINUX_VERSION}+git${SRCPV}"
