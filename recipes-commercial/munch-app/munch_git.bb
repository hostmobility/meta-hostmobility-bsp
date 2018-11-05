SUMMARY = "munch"
DESCRIPTION = "Memory test application that will use the memmory until it is killed by linux. Usefull to know how much memmory that is left free."

SUBPATH = "apps/munch"

require recipes-commercial/common/revision.inc

S = "${WORKDIR}/munch"

EXTRA_OEMAKE = 'CC="${CC}" CFLAGS="${CFLAGS}" LDFLAGS="${LDFLAGS}"'

do_compile () {
    oe_runmake
}

do_install() {
    install -d ${D}/usr/local/bin
    install -m 744 ${B}/munch-mem-test ${D}/usr/local/bin
}

FILES_${PN} += "/usr/local/bin/*"
