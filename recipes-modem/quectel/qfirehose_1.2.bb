SUMMARY = "Update quectel modems"
DESCRIPTION = "QFirehose fw update quectel modems"

BBVERBOSE = "1"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/qfirehose-1.2/NOTICE;md5=15172a07c9a201b68c4dc6611f280362"

SRCREV = "f77d6729dcf72295e2c024782946cf8ac8a2d8c1"
PV = "1.2"

SRC_URI[md5sum] = "009c73c6e18970d201b3168158cff2f3"
SRC_URI[sha256sum] = "0fa00df5e70e3044b294b41c6f1d1d28254997bbe0c2b9fbfacaf62493f4e769"
SRC_URI = " \
    https://github.com/nippynetworks/qfirehose/archive/refs/tags/1.2.tar.gz \
    file://modem_firmware_update_helper.sh \
"

RDEPENDS:${PN} = "bash"

EXTRA_OEMAKE = 'CC="${CC}" CFLAGS="${CFLAGS}" LDFLAGS="${LDFLAGS}"'

S = "${WORKDIR}/qfirehose-${PV}"


do_compile () {
   oe_runmake  
}

do_install() {
    install -d ${D}/usr/local/bin
    install -m 744 ${S}/QFirehose ${D}/usr/local/bin

    install -d ${D}/opt/hm
    install -m 744 ${WORKDIR}/modem_firmware_update_helper.sh ${D}/opt/hm
}

FILES:${PN} += "/usr/local/bin"
FILES:${PN} += "/usr/local/bin/QFirehose"
FILES:${PN} += "/opt/hm"
FILES:${PN} += "/opt/hm/modem_firmware_update_helper.sh"
INSANE_SKIP:${PN} = "already-stripped ldflags"
