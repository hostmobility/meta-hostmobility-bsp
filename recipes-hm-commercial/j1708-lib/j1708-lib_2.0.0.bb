SUMMARY = "mx5-j1708-lib"
DESCRIPTION = "Library to communicate with automotive J1708 network bus on MX-5."

SUBPATH = ""

require recipes-hm-commercial/common/revision.inc

inherit pkgconfig

S = "${WORKDIR}/git/Libraries/j1708/src"

PV .= "+git${SRCPV}"

PROVIDES = "j1708-lib"
RPROVIDES_${PN} += " libj1708.so"

do_compile () {
    cd ${S}
    oe_runmake
}

do_install() {

    install -d ${D}${includedir}/j1708
    install -d ${D}${libdir}

    install -m 744 ${B}/build/release/libj1708.so ${D}${libdir}/libj1708.so.${PV}

    ln -sf libj1708.so.${PV} ${D}${libdir}/libj1708.so

    #Install headers
    install -m 744 ${B}/core/api.h ${D}${includedir}/j1708/api.h
    install -m 744 ${B}/core/commons.h ${D}${includedir}/j1708/commons.h
}

INSANE_SKIP_${PN} = "dev-so"

SOLIBS = "libj1708.so.${PV}"
FILES_SOLIBSDEV = ""
FILES_${PN} = "${libdir}/${SOLIBS} ${libdir}/libj1708.so ${includedir}/j1708/* "
FILES_SOLIBSDEV ?= "${libdir}/lib*${SOLIBSDEV}"
FILES_${PN}-dev = "${FILES_SOLIBSDEV}"

BBCLASSEXTEND = "nativesdk"

