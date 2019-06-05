SUMMARY = "flexraysequence"
DESCRIPTION = "flexraysequence is a flexray-utility to send/receive packets"
SECTION = "net"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

DEPENDS = ""

# http://www.artila.com/download/9G20/Linux/Example/FlexrayBus/libsocketflexray/flexraysequence.c
SRC_URI = " \
	file://flexraysequence.c \
	file://COPYING \
"
SRCREV = "${AUTOREV}"

#SRC_URI[md5sum] = "3af010bf01d5203d2077335f91ef2213"
#SRC_URI[sha256] = "4d7303a849aecedb6025d74e6e608f79ca0b730728eee70b9348b5591db5d11d"

inherit autotools

do_compile() {
    cd ${WORKDIR}/
    ${CC} -g -I${STAGING_DIR}/usr/include -DVERSION=5.0 -DPF_FLEXRAY=40 ${WORKDIR}/flexraysequence.c -o ${WORKDIR}/flexraysequence
}

do_install () {
    install -d ${D}/usr/bin	   
    install -m 0755 ${WORKDIR}/flexraysequence     ${D}/usr/bin/flexraysequence
}

FILES_${PN} += "/usr/bin/flexraysequence"

