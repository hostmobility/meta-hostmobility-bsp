SUMMARY = "j1708-test"
DESCRIPTION = "Application to J1708 network bus on MX-4"

RDEPENDS_${PN} = "j1708-lib"
DEPENDS = "j1708-lib"

SUBPATH = "apps/j1708-test"

require recipes-commercial/common/revision.inc

S = "${WORKDIR}/j1708-test"

do_compile () {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 744 ${B}/j1708-test ${D}${bindir}/j1708-test
}

FILES_${PN} = "${bindir}/j1708-test"

