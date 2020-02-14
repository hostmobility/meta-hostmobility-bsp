SUMMARY = "FlexRay start/resume and suspend service"
SECTION = "console"
LICENSE = "CLOSED"
SRC_URI = " \
	file://flexray_start.service \
	file://flexray_start.sh \
	file://flexray_suspend.service \
	file://flexray_suspend.sh \
"

S = "${WORKDIR}"

inherit systemd

DEPENDS = "virtual/kernel"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "flexray_suspend.service flexray_start.service"
RDEPENDS_${PN} = "bash"

do_install_append() {
        install -d ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/flexray_start.service ${D}${systemd_unitdir}/system/flexray_start.service
        install -m 644 ${WORKDIR}/flexray_suspend.service ${D}${systemd_unitdir}/system/flexray_suspend.service
        install -d ${D}${bindir}
        install -m 0755 ${WORKDIR}/flexray_suspend.sh ${D}${bindir}/flexray_suspend.sh
        install -m 0755 ${WORKDIR}/flexray_start.sh ${D}${bindir}/flexray_start.sh
}