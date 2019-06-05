SUMMARY = "FlexRay control tool"
DESCRIPTION = "flexrayctl is a small utility get statistics and control the FlexRay controller"
SECTION = "net"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

PV = "0.0+gitr${SRCPV}"
SRCREV = "${AUTOREV}"

BRANCH_flexrayctl ?= "master"
BRANCH = "${BRANCH_flexrayctl}"

SRC_URI = " \
	git://git@github.com/hostmobility/mx-flexray-utils.git;protocol=ssh;branch=${BRANCH} \
	file://COPYING \
"

S = "${WORKDIR}/git/flexrayctl"

inherit cmake

DEPENDS = ""
