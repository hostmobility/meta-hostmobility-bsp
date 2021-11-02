DESCRIPTION = "udev rule for FR-MCU ethernet interface"
SECTION = "udev"
LICENSE = "CLOSED"
SRC_URI = "file://fr-mcu.rules"

do_install() {
    install -d ${D}/${sysconfdir}/udev/rules.d
    install -m 0644 ${WORKDIR}/fr-mcu.rules ${D}/${sysconfdir}/udev/rules.d/
}
