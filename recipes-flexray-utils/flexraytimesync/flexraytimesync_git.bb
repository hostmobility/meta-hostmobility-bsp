SUMMARY = "FlexRay Timesync Daemon"
SECTION = "console"
LICENSE = "CLOSED"
PV = "0.0+gitr${SRCPV}"
SRCREV = "${AUTOREV}"
BRANCH_flexraytimesync ?= "master"
BRANCH = "${BRANCH_flexraytimesync}"
SRC_URI = " \
	git://git@github.com/hostmobility/mx-flexray-utils.git;protocol=ssh;branch=${BRANCH} \
	file://service \
"

S = "${WORKDIR}/git/flexraytimesync"

inherit cmake systemd

DEPENDS = ""

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "flexraytimesync.service"

do_install_append() {
        install -d ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/service ${D}${systemd_unitdir}/system/${PN}.service
}