DESCRIPTION = "udev rule for persistent names of CAN and Ethernet"
SECTION = "udev"
LICENSE = "CLOSED"
SRC_URI = "file://10-hm-interface-names.rules"

do_install() {
    install -d ${D}/${sysconfdir}/udev/rules.d
    install -m 0644 ${WORKDIR}/10-hm-interface-names.rules ${D}/${sysconfdir}/udev/rules.d/
}
