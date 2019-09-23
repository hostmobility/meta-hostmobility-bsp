SUMMARY = "pic-firmware"
DESCRIPTION = "Firmware files for CO-CPU on MX-4"

SUBPATH = "mx-flexray-firmware"

require recipes-commercial/common/revision.inc
DEPENDS = "lrzsz"

S = "${WORKDIR}/mx-flexray-firmware"

do_install() {
    install -d ${D}/opt/hm/frmcu
    install -m 744 ${B}/mx-flexray.fw ${D}/opt/hm/frmcu/mx-flexray.fw
    install -m 744 ${B}/mx-flexray.minicom ${D}/opt/hm/frmcu/mx-flexray.minicom
}

FILES_${PN} += "/opt/hm/frmcu/*"

