SUMMARY = "U-boot update script for mx5"
LICENSE = "CLOSED"

SRC_URI = "file://update.cmd"

DEPENDS = "u-boot-mkimage-native"


do_compile() {
    uboot-mkimage -A arm -T script -C none -n "Update script" -d "${WORKDIR}/update.cmd" update.scr
}

inherit deploy

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 update.scr ${DEPLOYDIR}
}

addtask do_deploy after do_compile before do_build
