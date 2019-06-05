SUMMARY = "FlexRay dump tool"
DESCRIPTION = "flexraydump is a small utility to show FlexRay packets on FlexRay interface"
SECTION = "net"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
PV = "0.0+gitr${SRCPV}"
SRCREV = "${AUTOREV}"
BRANCH_flexraydump ?= "master"
BRANCH = "${BRANCH_flexraydump}"
SRC_URI = " \
	git://git@github.com/hostmobility/mx-flexray-utils.git;protocol=ssh;branch=${BRANCH} \
        file://COPYING \
"

S = "${WORKDIR}/git/flexraydump"

inherit cmake

DEPENDS = ""

