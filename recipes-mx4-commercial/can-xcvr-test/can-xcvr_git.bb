SUMMARY = "can-xcvr"
DESCRIPTION = "Configuration tool for our "smart" CAN trancivers. It utilitize Linux spidev driver which gives access to an SPi bus in user-space."

SUBPATH = "apps/can-xcvr"

require recipes-mx4-commercial/common/revision.inc

S = "${WORKDIR}/can-xcvr"

EXTRA_OEMAKE = 'CC="${CC}" CFLAGS="${CFLAGS}" LDFLAGS="${LDFLAGS}"'

inherit systemd

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "can-xcvr.service"
RDEPENDS:${PN} = "bash"

do_compile () {
    oe_runmake
}

do_install() {
    install -d ${D}/usr/local/bin
    install -m 744 ${B}/can-xcvr ${D}/usr/local/bin

    install -d ${D}${systemd_unitdir}/system
    install -m 644 ${S}/can-xcvr.service ${D}${systemd_unitdir}/system/${PN}.service

    install -d ${D}${bindir}
    install -m 744 ${S}/can-xcvr_start.sh ${D}${bindir}/can-xcvr_start.sh
}

FILES:${PN} += "/usr/local/bin/*"
