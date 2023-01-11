SUMMARY = "FlexRay UDP Daemon"
SECTION = "console"
LICENSE = "CLOSED"

require recipes-flexray-utils/common/revision.inc

S = "${WORKDIR}/git/flexrayd"

inherit cmake

DEPENDS = "virtual/kernel"


do_configure_prepend () {
    install -d 0755 ${S}/src/linux
    install -m 0644 ${THISDIR}/../flexrayheader/flexray.h   ${S}/src/linux/flexray.h    
}
