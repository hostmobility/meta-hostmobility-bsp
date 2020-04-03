SUMMARY = "flexray-firmware"
DESCRIPTION = "Firmware files for flexray chip"
SUBPATH = "mx-flexray-firmware"
LICENSE = "CLOSED"
DEPENDS = "lrzsz"

BRANCH = "master"
SRCREV = "28d862438b6eb4d5d0ee59617d62ce7474eab414"
SRC_URI = "git://git@github.com/hostmobility/mx-flexray-utils.git;protocol=ssh;branch=${BRANCH}"
PV = "0.0+gitr${SRCPV}"

S = "${WORKDIR}/git/mx-flexray-firmware"

do_install() {
    install -d ${D}/opt/hm/frmcu
    install -m 744 ${S}/mx-flexray.fw ${D}/opt/hm/frmcu/mx-flexray.fw
    install -m 744 ${S}/mx-flexray.minicom ${D}/opt/hm/frmcu/mx-flexray.minicom
}

FILES_${PN} += "/opt/hm/frmcu/*"

