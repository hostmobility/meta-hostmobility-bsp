SUMMARY = "FlexRay dump tool"
DESCRIPTION = "flexraydump is a small utility to show FlexRay packets on FlexRay interface"
SECTION = "net"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

require recipes-flexray-utils/common/revision.inc

SRC_URI += "file://COPYING"

S = "${WORKDIR}/git/flexraydump"

do_configure:prepend () {
    install -d 0755 ${S}/src/linux
    install -m 0644 ${THISDIR}/../flexrayheader/flexray.h   ${S}/src/linux/flexray.h    
}
inherit cmake

DEPENDS = "virtual/kernel"

