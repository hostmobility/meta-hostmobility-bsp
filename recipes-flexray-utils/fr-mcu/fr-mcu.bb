DESCRIPTION = "udev rule for FR-MCU ethernet interface"
SECTION = "udev"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

SRC_URI = "file://fr-mcu.rules"

do_install() {
    install -d ${D}/${sysconfdir}/udev/rules.d
    install -m 0644 ${WORKDIR}/fr-mcu.rules ${D}/${sysconfdir}/udev/rules.d/
}
