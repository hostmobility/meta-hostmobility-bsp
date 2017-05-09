SUMMARY = "j1708-lib"
DESCRIPTION = "Library to communicate with automotive J1708 network bus on MX-4"

SUBPATH = "lib/j1708"

require recipes-commercial/common/revision.inc

S = "${WORKDIR}/j1708/src"

EXTRA_OEMAKE_mx4-c61 = "BOARD=c61"

EXTRA_OEMAKE_mx4-mil = "BOARD=mil"

do_compile () {
    oe_runmake
}

do_install() {
    install -d ${D}${includedir}/j1708
    install -d ${D}${libdir}

    install -m 744 ${B}/build/release/libj1708.so ${D}${libdir}/libj1708.so.${PV}

    ln -sf ${D}${libdir}/libj1708.so.${PV} ${D}${libdir}/libj1708.so

    #Install headers
    install -m 744 ${B}/core/api.h ${D}${includedir}/j1708/api.h
    install -m 744 ${B}/core/commons.h ${D}${includedir}/j1708/commons.h
}

FILES_${PN} = " \
    ${libdir}/libj1708.so* \
    ${includedir}/j1708/* \
"

BBCLASSEXTEND = "nativesdk"
