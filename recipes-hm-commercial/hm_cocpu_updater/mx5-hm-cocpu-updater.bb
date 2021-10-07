SUMMARY = "hm_cocpu_updater"
DESCRIPTION = "Application to update co-cpu firmware on MX-5"

SUBPATH = ""

require recipes-hm-commercial/common/revision.inc

S = "${WORKDIR}/git/hm_cocpu_updater"


EXTRA_OEMAKE = 'CC="${CXX}"'

do_compile () {
    cd ${S}
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 744 ${B}/hm_cocpu_updater ${D}${bindir}/hm_cocpu_updater
}

FILES_${PN} = "${bindir}/hm_cocpu_updater"
