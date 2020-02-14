SUMMARY = "FlexRay Twatchdog"
SECTION = "net"
LICENSE = "CLOSED"
PV = "0.0+gitr${SRCPV}"
SRCREV = "${AUTOREV}"
BRANCH_flexraywd ?= "mainline_4.19"
BRANCH = "${BRANCH_flexraywd}"
SRC_URI = " \
	git://git@github.com/hostmobility/mx-flexray-utils.git;protocol=ssh;branch=${BRANCH} \
	file://service \
	file://flexray_boot.sh \
	file://flexray_shutdown.sh \
"

S = "${WORKDIR}/git/flexraywd"

inherit systemd

DEPENDS = "virtual/kernel"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "flexraywd.service"
RDEPENDS_${PN} = "bash"

do_configure_prepend () {
    install -d 0755 ${S}/src/linux
    install -m 0644 ${THISDIR}/../flexrayheader/flexray.h   ${S}/src/linux/flexray.h    
}

do_install_append() {
        install -d ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/service ${D}${systemd_unitdir}/system/${PN}.service

        install -d ${D}${bindir}
        install -m 0755 ${S}/flexraywd ${D}${bindir}/flexraywd
        install -m 0755 ${WORKDIR}/flexray_boot.sh ${D}${bindir}/flexray_boot.sh
        install -m 0755 ${WORKDIR}/flexray_shutdown.sh ${D}${bindir}/flexray_shutdown.sh
        # make a dummy file for first time boot the lazy way.
        install -m 0755 ${WORKDIR}/flexray_shutdown.sh ${D}${bindir}/first_boot_after_update_flexray.txt
}