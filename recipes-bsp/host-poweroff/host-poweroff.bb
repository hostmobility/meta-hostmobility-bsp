LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

COMPATIBLE_MACHINE = "(imx8mp-var-dart|tegra|vf60)"

SRC_URI:vf60 = "\
    file://host-vf-poweroff.service \
"

SRC_URI:tegra = "\
    file://host-tegra-poweroff.service \
"

SRC_URI:imx8mp-var-dart = "\
    file://host-watchdog-poweroff.service \
"

inherit systemd

DEPENDS:vf60 = "kernel-module-pic"
DEPENDS:tegra = "kernel-module-pic"
DEPENDS:imx8mp-var-dart = "host-watchdog-driver"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "host-poweroff.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_install:tegra() {
    install -Dm 0644 ${WORKDIR}/host-tegra-poweroff.service ${D}${systemd_unitdir}/system/host-poweroff.service
}

do_install:vf60() {
    install -Dm 0644 ${WORKDIR}/host-vf-poweroff.service ${D}${systemd_system_unitdir}/host-poweroff.service
}

do_install:imx8mp-var-dart() {
    install -Dm 0644 ${WORKDIR}/host-watchdog-poweroff.service ${D}${systemd_system_unitdir}/host-poweroff.service
}

FILES:${PN} = "\
    ${systemd_unitdir}/system/host-poweroff.service \
"
