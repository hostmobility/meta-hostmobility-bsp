SUMMARY = "Legacy Network Names Script and Service"
DESCRIPTION = "A script and service to handle legacy network names"
LICENSE = "CLOSED"
PR = "r0"

SRC_URI = "file://legacy-network-names.sh \
           file://hm-legacy-names.service"

S = "${WORKDIR}"

inherit systemd

RDEPENDS:${PN} = "bash"

SYSTEMD_PACKAGES = "${PN}"

SYSTEMD_SERVICE:${PN} = "hm-legacy-names.service"

do_install() {
    install -d ${D}/opt/hm
    install -m 0755 ${WORKDIR}/legacy-network-names.sh ${D}/opt/hm/
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/hm-legacy-names.service ${D}${systemd_unitdir}/system/
}

FILES:${PN} += "\
    /opt/hm/legacy-network-names.sh \
    ${systemd_unitdir}/system/hm-legacy-names.service \
"


INITSCRIPT_NAME = "hm-legacy-names"
INITSCRIPT_PARAMS = "defaults"
SYSTEMD_AUTO_ENABLE = "enable"

