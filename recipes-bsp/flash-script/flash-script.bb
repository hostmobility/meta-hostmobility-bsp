SUMMARY = "U-boot update script for mxv and hmx"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://flash.cmd"

SRC_URI:append:verdin-am62-hmm = " file://flash.cmd"

DEPENDS = "u-boot-mkimage-native"


do_compile() {
    uboot-mkimage -A arm -T script -C none -n "Update script" -d "${WORKDIR}/flash.cmd" flash.scr
}

inherit deploy

do_deploy:append:mx5-pt() {
    install -d ${DEPLOYDIR}
    install -m 0644 flash.scr ${DEPLOYDIR}/flashmx5.scr
}

do_deploy:append:imx8mp-var-dart-hmx1() {
    install -d ${DEPLOYDIR}
    install -m 0644 flash.scr ${DEPLOYDIR}/hmx_boot.scr
}

do_deploy:append:verdin-am62-hmm() {
    install -m 0644 flash.scr ${DEPLOYDIR}/hmm_boot.scr
}

do_deploy() {
    install -d ${DEPLOYDIR}
}

addtask do_deploy after do_compile before do_build


ALLOW_EMPTY:${PN} = "1"
RDEPENDS:${PN} = ""