SUMMARY = "FlexRay UDP Daemon"
SECTION = "console"
LICENSE = "CLOSED"
PV = "0.0+gitr${SRCPV}"
SRCREV = "${AUTOREV}"
BRANCH_flexrayd ?= "mainline_4.19"
BRANCH = "${BRANCH_flexrayd}"
SRC_URI = " \
	git://git@github.com/hostmobility/mx-flexray-utils.git;protocol=ssh;branch=${BRANCH} \
	file://service \
"

S = "${WORKDIR}/git/flexrayd"

inherit cmake systemd

DEPENDS = "virtual/kernel"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "flexrayd.service"

do_configure_prepend () {
    install -d 0755 ${S}/src/linux
    install -m 0644 ${THISDIR}/../flexrayheader/flexray.h   ${S}/src/linux/flexray.h    
}

do_install_append() {
        install -d ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/service ${D}${systemd_unitdir}/system/${PN}.service
}