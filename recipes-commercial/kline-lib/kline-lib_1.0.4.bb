SUMMARY = "kline-lib"
DESCRIPTION = "Library to communicate with automotive K-LINE network bus on MX-4"

SUBPATH = "lib/kline"

require recipes-commercial/common/revision.inc

S = "${WORKDIR}/kline/"

do_compile () {
    oe_runmake
}

do_install() {
    install -d ${D}${includedir}
    install -d ${D}${libdir}

    install -m 744 ${B}/libptdkline.so.${PV} ${D}${libdir}/libptdkline.so.${PV}

    ln -sf ${libdir}/libptdkline.so.${PV} ${D}${libdir}/libptdkline.so

    #Install headers
    install -m 744 ${B}/ptd_kline.h ${D}${includedir}/ptd_kline.h
}

FILES_${PN} = " \
    ${libdir}/libptdkline.so* \
    ${includedir}/ptdptd_kline.h \
"
