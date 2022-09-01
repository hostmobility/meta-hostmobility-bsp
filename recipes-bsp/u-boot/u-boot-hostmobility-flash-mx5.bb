SUMMARY = "U-boot update script for mx5"
LICENSE = "CLOSED"

SRC_URI = "file://flashmx5.cmd"

COMPATIBLE_MACHINE = "(mx5-pt)"
DEPENDS = "u-boot-mkimage-native"


do_compile() {
    uboot-mkimage -A arm -T script -C none -n "Update script" -d "${WORKDIR}/flashmx5.cmd" flashmx5.scr
}

inherit deploy

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 flashmx5.scr ${DEPLOYDIR}
}

addtask do_deploy after do_compile before do_build
