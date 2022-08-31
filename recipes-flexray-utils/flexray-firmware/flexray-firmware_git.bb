SUMMARY = "flexray-firmware"
DESCRIPTION = "Firmware files for flexray chip"
SUBPATH = "mx-flexray-firmware"
LICENSE = "CLOSED"
DEPENDS = "lrzsz"

require recipes-flexray-utils/common/revision.inc

S = "${WORKDIR}/git/mx-flexray-firmware"

do_install() {
    install -d ${D}/opt/hm/frmcu
    install -m 744 ${S}/mx-flexray.fw ${D}/opt/hm/frmcu/mx-flexray.fw
    install -m 744 ${S}/mx-flexray.minicom ${D}/opt/hm/frmcu/mx-flexray.minicom
}

FILES:${PN} += "/opt/hm/frmcu/*"

