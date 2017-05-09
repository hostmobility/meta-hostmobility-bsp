SUMMARY = "pic-firmware"
DESCRIPTION = "Firmware files for CO-CPU on MX-4"

SUBPATH = "pic-firmware"

inherit hostmobility-helpers

require recipes-commercial/common/revision.inc

S = "${WORKDIR}/pic-firmware"

do_install() {
    install -d ${D}/opt/hm/pic

    target_machine=`get_mx4_type_from_machine ${MACHINE}`

    install -m 744 ${B}/${target_machine}-app/latest.hex ${D}/opt/hm/pic/app.hex
    install -m 744 ${B}/${target_machine}-bl/latest.hex ${D}/opt/hm/pic/bl.hex
    install -m 744 ${B}/${target_machine}-bl-app/latest.hex ${D}/opt/hm/pic/bl-app.hex
}

FILES_${PN} = "/opt/hm/pic/*.hex"

