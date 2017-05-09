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

    # Placeholders until we figure out what we want to put here.
    echo "unknown" > ${D}/${sysconfdir}/platform-version
    echo "unknown" > ${D}/${sysconfdir}/platform-branch-name
    echo "unknown" > ${D}/${sysconfdir}/platform-bsp-version
    echo "unknown" > ${D}/${sysconfdir}/platform-build-tag

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

PACKAGES = "mx4-utils-info mx4-utils-scripts mx4-utils-autostart mx4-utils-mount-config"

FILES_mx4-utils-info = "\
    ${sysconfdir}/first_boot_after_update.txt \
    ${sysconfdir}/platform-system-type \
    ${sysconfdir}/platform-board-type \
    ${sysconfdir}/platform-version \
    ${sysconfdir}/platform-branch-name \
    ${sysconfdir}/platform-bsp-version \
    ${sysconfdir}/platform-build-tag \
"

RDEPENDS_mx4-utils-scripts = "bash"
FILES_mx4-utils-scripts = "\
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
"

FILES_mx4-utils-scripts_mx4-mil += "\
    /opt/hm/analog_calibration.sh \
    /opt/hm/date2pic.sh \
    /opt/hm/pic2rtc.sh \
"

RDEPENDS_mx4-utils-autostart = "bash"
FILES_mx4-utils-autostart = "/opt/hm/autostart.sh ${systemd_unitdir}/system/autostart.service"
SYSTEMD_SERVICE_mx4-utils-autostart = "autostart.service"

RDEPENDS_mx4-utils-mount-config = "bash"
FILES_mx4-utils-mount-config = "/opt/hm/mount_config.sh ${systemd_unitdir}/system/mount-config.service"
SYSTEMD_SERVICE_mx4-utils-mount-config = "mount-config.service"

