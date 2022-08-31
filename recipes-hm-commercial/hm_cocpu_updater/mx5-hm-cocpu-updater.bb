SUMMARY = "hm_cocpu_updater"
DESCRIPTION = "Application to update co-cpu firmware on MX-5"

SUBPATH = ""

RDEPENDS:${PN} += "bash"


require recipes-hm-commercial/common/revision.inc

S = "${WORKDIR}/git/hm_cocpu_updater"


EXTRA_OEMAKE = 'CC="${CXX}"'

do_compile () {
    cd ${S}
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 744 ${B}/hm_cocpu_updater ${D}${bindir}/hm_cocpu_updater
    install -m 744 ${S}/update_cocpu_firmware.sh ${D}${bindir}/update_cocpu_firmware.sh
}

FILES:${PN} = " \
  ${bindir}/hm_cocpu_updater \
  ${bindir}/update_cocpu_firmware.sh \
 "
