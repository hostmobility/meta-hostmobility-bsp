SUMMARY = "FlexRay control tool"
DESCRIPTION = "flexrayctl is a small utility get statistics and control the FlexRay controller"
SECTION = "net"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

require recipes-flexray-utils/common/revision.inc

SRC_URI += "file://COPYING"

S = "${WORKDIR}/git/flexrayctl"

inherit cmake

DEPENDS = ""
