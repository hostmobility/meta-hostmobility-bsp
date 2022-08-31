SUMMARY = "FlexRay Watchdog"
SECTION = "net"
LICENSE = "CLOSED"

require recipes-flexray-utils/common/revision.inc

SRC_URI += " \
	file://service \
	file://flexray_boot.sh \
	file://flexray_shutdown.sh \
"

S = "${WORKDIR}/git/flexraywd"

inherit systemd

DEPENDS = "virtual/kernel"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "flexraywd.service"
RDEPENDS:${PN} = "bash"

do_configure:prepend () {
    install -d 0755 ${S}/src/linux
    install -m 0644 ${THISDIR}/../flexrayheader/flexray.h   ${S}/src/linux/flexray.h    
}

do_install:append() {
        install -d ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/service ${D}${systemd_unitdir}/system/${PN}.service

        install -d ${D}${bindir}
        install -m 0755 ${S}/flexraywd ${D}${bindir}/flexraywd
        install -m 0755 ${WORKDIR}/flexray_boot.sh ${D}${bindir}/flexray_boot.sh
        install -m 0755 ${WORKDIR}/flexray_shutdown.sh ${D}${bindir}/flexray_shutdown.sh
        # make a dummy file for first time boot the lazy way.
        install -m 0755 ${WORKDIR}/flexray_shutdown.sh ${D}${bindir}/first_boot_after_update_flexray.txt
}
