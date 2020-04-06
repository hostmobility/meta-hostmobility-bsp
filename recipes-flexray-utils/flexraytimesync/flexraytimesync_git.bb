SUMMARY = "FlexRay Timesync Daemon"
SECTION = "console"
LICENSE = "CLOSED"

require recipes-flexray-utils/common/revision.inc

SRC_URI += "file://service"

S = "${WORKDIR}/git/flexraytimesync"

inherit cmake systemd

DEPENDS = ""

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "flexraytimesync.service"

do_install_append() {
        install -d ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/service ${D}${systemd_unitdir}/system/${PN}.service
}
