DESCRIPTION = "udev rule and script for persistent names of CAN and Ethernet"
SECTION = "udev"
LICENSE = "CLOSED"
SRC_URI = "\
	file://10-hm-interface-names.rules \
	file://hm-interface-names.service \
	"

inherit systemd
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "hm-interface-names.service"

FILES:${PN} += "\
    ${systemd_unitdir}/system/hm-interface-names.service \
"


do_install() {
    install -d ${D}/${sysconfdir}/udev/rules.d
    install -m 0644 ${WORKDIR}/10-hm-interface-names.rules ${D}/${sysconfdir}/udev/rules.d/
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/hm-interface-names.service ${D}${systemd_unitdir}/system/
}

INITSCRIPT_NAME = "hm-interface-names"
INITSCRIPT_PARAMS = "defaults"
SYSTEMD_AUTO_ENABLE = "enable"

RDEPENDS:${PN} = "hm-stable-names"
