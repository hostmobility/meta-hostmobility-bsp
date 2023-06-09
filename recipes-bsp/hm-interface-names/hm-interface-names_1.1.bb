SUMMARY = "Service and script for persistent names of CAN and Ethernet"
DESCRIPTION = "A service that runs script for reordering network and CAN interfaces to keep the canX and ethX names but order them in the same order regardless of which gets created first by the drivers "

SECTION = "udev"
LICENSE = "CLOSED"
SRC_URI = "\
    file://hm-interface-names.service \
"

inherit systemd
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "hm-interface-names.service"

FILES:${PN} += "\
    ${systemd_unitdir}/system/hm-interface-names.service \
"


do_install() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/hm-interface-names.service ${D}${systemd_unitdir}/system/
}

INITSCRIPT_NAME = "hm-interface-names"
INITSCRIPT_PARAMS = "defaults"
SYSTEMD_AUTO_ENABLE = "enable"

RDEPENDS:${PN} = "hm-stable-names"
