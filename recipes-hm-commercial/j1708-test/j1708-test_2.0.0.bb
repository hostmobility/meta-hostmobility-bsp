SUMMARY = "j1708-test"
DESCRIPTION = "Application to J1708 network bus on MX-4"


DEPENDS += " j1708-lib"
RDEPENDS_${PN} += " libj1708.so"

inherit pkgconfig

SUBPATH = "Tools/j1708-test"

require recipes-hm-commercial/common/revision.inc

S = "${WORKDIR}/j1708-test"

export LDFLAGS = "-L${STAGING_LIBDIR_NATIVE}"

do_compile () {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 744 ${B}/j1708-test ${D}${bindir}/j1708-test
}

FILES_${PN} = "${bindir}/j1708-test"