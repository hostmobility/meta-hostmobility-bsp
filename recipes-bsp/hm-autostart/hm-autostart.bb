LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += "\
    file://autostart.service \
    file://autostart.sh \
"
inherit systemd

DEPENDS = "virtual/kernel"

SYSTEMD_PACKAGES = "${PN}"

SYSTEMD_SERVICE:${PN} = "autostart.service"
RDEPENDS:${PN} = "bash"

do_install() {
    install -d ${D}/${sysconfdir}
    install -d ${D}${systemd_unitdir}/system/
    install -d ${D}/opt/hm/

    # Legacy file and method to indicate that it is first-time boot
    touch ${D}/${sysconfdir}/first_boot_after_update.txt
    # Indicate that the hostname file is not yet fully written
    touch ${D}/${sysconfdir}/hostname_from_eeprom_pending.txt

    install -m 0644 ${WORKDIR}/autostart.service ${D}${systemd_unitdir}/system/autostart.service
    install -m 0755 ${WORKDIR}/autostart.sh ${D}/opt/hm/autostart.sh
}
FILES:${PN} = "\
    /opt/hm/autostart.sh \
    ${systemd_unitdir}/system/autostart.service \
    ${sysconfdir}/first_boot_after_update.txt \
    ${sysconfdir}/hostname_from_eeprom_pending.txt \
"
