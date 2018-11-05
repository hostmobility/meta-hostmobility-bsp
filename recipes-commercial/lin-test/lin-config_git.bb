SUMMARY = "lin-config"
DESCRIPTION = "Library to communicate with automotive LIN network bus on MX-4"

SUBPATH = "apps/lin-config"

require recipes-commercial/common/revision.inc

S = "${WORKDIR}/lin-config"

EXTRA_OEMAKE = 'CC="${CC}" CFLAGS="${CFLAGS}" LDFLAGS="${LDFLAGS}"'

do_compile () {
    oe_runmake
}

do_install() {
    install -d ${D}/usr/local/bin
    install -m 744 ${B}/lin-config ${D}/usr/local/bin
}

FILES_${PN} += "/usr/local/bin/*"
