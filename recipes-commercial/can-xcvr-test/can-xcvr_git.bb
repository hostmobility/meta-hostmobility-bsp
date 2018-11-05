SUMMARY = "can-xcvr"
DESCRIPTION = "Configuration tool for our "smart" CAN trancivers. It utilitize Linux spidev driver which gives access to an SPi bus in user-space."

SUBPATH = "apps/can-xcvr"

require recipes-commercial/common/revision.inc

S = "${WORKDIR}/can-xcvr"

EXTRA_OEMAKE = 'CC="${CC}" CFLAGS="${CFLAGS}" LDFLAGS="${LDFLAGS}"'

do_compile () {
    oe_runmake
}

do_install() {
    install -d ${D}/usr/local/bin
    install -m 744 ${B}/can-xcvr ${D}/usr/local/bin
}

FILES_${PN} += "/usr/local/bin/*"
