SUMMARY = "hm-cocpu-updater-service"
DESCRIPTION = "systemd service to keep co-cpu firmware updated"
LICENSE = "CLOSED"
SERVICE_NAME="mx5-hm-cocpu-updater.service"

SUBPATH = ""
require recipes-hm-commercial/common/revision.inc
inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "${SERVICE_NAME}"

# The service needs the updater program and bash script installed
RDEPENDS:${PN} += "mx5-hm-cocpu-updater"
FILES:${PN} += "${systemd_unitdir}/system/${SERVICE_NAME}"

S = "${WORKDIR}/git/hm_cocpu_updater"

do_install:append() {
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${S}/${SERVICE_NAME} ${D}/${systemd_unitdir}/system
}

