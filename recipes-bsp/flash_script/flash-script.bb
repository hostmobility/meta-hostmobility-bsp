SUMMARY = "U-boot update script for mxv and hmx"
LICENSE = "CLOSED"

SRC_URI = "file://flash.cmd"

COMPATIBLE_MACHINE = "(mxv-base | imx8mp-var-dart)"
DEPENDS = "u-boot-mkimage-native"


do_compile() {
    uboot-mkimage -A arm -T script -C none -n "Update script" -d "${WORKDIR}/flash.cmd" flash.scr
}

inherit deploy

do_deploy:mxv-base() {
    install -d ${DEPLOYDIR}
    install -m 0644 flash.scr ${DEPLOYDIR}/flashmx5.scr
}

do_deploy:imx8mp-var-dart() {
    install -d ${DEPLOYDIR}
    install -m 0644 flash.scr ${DEPLOYDIR}/hmx_boot.scr
}

addtask do_deploy after do_compile before do_build
