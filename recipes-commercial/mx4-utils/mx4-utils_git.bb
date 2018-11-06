SUMMARY = "mx4-utils"
DESCRIPTION = "Collection of utility scripts which is part of MX-4 Board Support Package"

SUBPATH = "scripts/"

inherit hostmobility-helpers

require recipes-commercial/common/revision.inc

SRC_URI += "\
    file://autostart.service \
    file://mount-config.service \
"

S = "${WORKDIR}/scripts"

do_install() {
    install -d ${D}/${sysconfdir}
    install -d ${D}${systemd_unitdir}/system/
    install -d ${D}/opt/hm/


    # Legacy file and method to indicate that it is first-time boot
    touch ${D}/${sysconfdir}/first_boot_after_update.txt

    system_type=`get_mx4_type_from_machine ${MACHINE}`
    echo ${system_type} > ${D}/${sysconfdir}/platform-system-type

    echo ${BUILD_TAG} > ${D}/${sysconfdir}/platform-build-tag

    install -m 744 ${B}/mx4/*.sh ${D}/opt/hm/

    install -m 0644 ${WORKDIR}/autostart.service ${D}${systemd_unitdir}/system/autostart.service
    install -m 0644 ${WORKDIR}/mount-config.service ${D}${systemd_unitdir}/system/mount-config.service
}

do_install_append_tegra2() {
    echo "colibri-t20" > ${D}/${sysconfdir}/platform-board-type
}

do_install_append_tegra3() {
    echo "colibri-t30" > ${D}/${sysconfdir}/platform-board-type
}

do_install_append_vf() {
    echo "colibri-vf" > ${D}/${sysconfdir}/platform-board-type
}

do_install_append_mx4-mil() {
    install -m 744 ${B}/mil/*.sh ${D}/opt/hm/
}

PACKAGES += "${PN}-scripts ${PN}-autostart ${PN}-mount-config"

FILES_${PN} = "\
    ${sysconfdir}/first_boot_after_update.txt \
    ${sysconfdir}/platform-system-type \
    ${sysconfdir}/platform-board-type \
    ${sysconfdir}/platform-version \
    ${sysconfdir}/platform-branch-name \
    ${sysconfdir}/platform-bsp-version \
    ${sysconfdir}/platform-build-tag \
"

RDEPENDS_${PN}-scripts = "bash"
FILES_${PN}-scripts = "\
    /opt/hm/go_to_sleep.sh \
    /opt/hm/load_module.sh \
    /opt/hm/modem_status.sh \
    /opt/hm/pic_upgrade.sh \
    /opt/hm/platform_setup.sh \
    /opt/hm/reset_cause.sh \
    /opt/hm/set_led_flash.sh \
    /opt/hm/setup_env.sh \
    /opt/hm/shutdown.sh \
    /opt/hm/unload_module.sh \
    /opt/hm/version_script.sh \
    /opt/hm/frmcu_update.sh \
"

FILES_${PN}-scripts_mx4-mil += "\
    /opt/hm/analog_calibration.sh \
    /opt/hm/date2pic.sh \
    /opt/hm/pic2rtc.sh \
"

inherit systemd

SYSTEMD_PACKAGES = "${PN}-autostart ${PN}-mount-config"

RDEPENDS_${PN}-autostart = "bash"
FILES_${PN}-autostart = "/opt/hm/autostart.sh ${systemd_unitdir}/system/autostart.service"
SYSTEMD_SERVICE_mx4-${PN}-autostart = "autostart.service"

RDEPENDS_${PN}-mount-config = "bash"
FILES_${PN}-mount-config = "/opt/hm/mount_config.sh ${systemd_unitdir}/system/mount-config.service"
SYSTEMD_SERVICE_${PN}-mount-config = "mount-config.service"
