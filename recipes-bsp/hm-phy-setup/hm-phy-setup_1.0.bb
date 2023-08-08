SUMMARY = "Service and script to setup Marvell 88Q2110 T1 ethernet phys"
DESCRIPTION = "Setup T1 phys using register access with mdio"

SECTION = "udev"
LICENSE = "CLOSED"
SRC_URI = "\
    file://hm-phy-setup.service \
    file://hm_phy_setup.py \
"

inherit systemd
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "hm-phy-setup.service"

FILES:${PN} += "\
    ${systemd_unitdir}/system/hm-phy-setup.service \
    /opt/hm/hm_phy_setup.py \
"


do_install() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/hm-phy-setup.service ${D}${systemd_unitdir}/system/
    install -d ${D}/opt/hm
    install -m 0755 ${WORKDIR}/hm_phy_setup.py ${D}/opt/hm/
}

INITSCRIPT_NAME = "hm-phy-setup"
INITSCRIPT_PARAMS = "defaults"
SYSTEMD_AUTO_ENABLE = "enable"

RDEPENDS:${PN} = "python3 mdio-tools"
