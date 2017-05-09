SUMMARY = "pic-programmer"
DESCRIPTION = "Program to update firmware of CO-CPU on MX-4"

SUBPATH = "apps/pic-programmer"

require recipes-commercial/common/revision.inc

S = "${WORKDIR}/pic-programmer"

do_compile () {
    oe_runmake
}

do_install() {
    install -d ${D}/opt/hm/pic
    install -m 744 ${B}/pic-programmer ${D}/opt/hm/pic/
}

FILES_${PN} = "/opt/hm/pic/pic-programmer"

