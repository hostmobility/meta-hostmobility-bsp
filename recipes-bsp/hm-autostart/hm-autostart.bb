LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += "\
    file://autostart.service \
    file://autostart.sh \
    file://rpmsg_lite_host_watchdog.bin \
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
    install -d ${D}/lib/firmware/

    # Legacy file and method to indicate that it is first-time boot
    touch ${D}/${sysconfdir}/first_boot_after_update.txt

    install -m 0644 ${WORKDIR}/autostart.service ${D}${systemd_unitdir}/system/autostart.service
    install -m 0755 ${WORKDIR}/autostart.sh ${D}/opt/hm/autostart.sh
    install -m 0755 ${WORKDIR}/rpmsg_lite_host_watchdog.bin ${D}/lib/firmware/rpmsg_lite_host_watchdog.bin
}
FILES:${PN} = "\
    /opt/hm/autostart.sh \
    ${systemd_unitdir}/system/autostart.service \
    ${sysconfdir}/first_boot_after_update.txt \
    /lib/firmware/rpmsg_lite_host_watchdog.bin \
"
