SUMMARY = "FlexRay Timesync Daemon"
SECTION = "console"
LICENSE = "CLOSED"

require recipes-flexray-utils/common/revision.inc

S = "${WORKDIR}/git/flexraytimesync"

inherit cmake 

DEPENDS = ""
