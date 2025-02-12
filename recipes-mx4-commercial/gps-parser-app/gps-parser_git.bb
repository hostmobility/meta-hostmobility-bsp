SUMMARY = "gps-parser"
DESCRIPTION = "GPS parser for gps data."

SUBPATH = "apps/gps-parser"

require recipes-mx4-commercial/common/revision.inc

S = "${WORKDIR}/gps-parser"

EXTRA_OEMAKE = 'CC="${CC}" CFLAGS="${CFLAGS}" LDFLAGS="${LDFLAGS}"'

do_compile () {
    oe_runmake
}

do_install() {
    install -d ${D}/usr/local/bin
    install -m 744 ${B}/gps-parser ${D}/usr/local/bin
}

FILES:${PN} += "/usr/local/bin/*"
