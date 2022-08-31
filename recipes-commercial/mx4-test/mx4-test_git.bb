SUMMARY = "mx4-test"
DESCRIPTION = "Collection of scripts useful when testing stuff on MX-4"

SUBPATH = "test/"

INSANE_SKIP:${PN} += "ldflags"

require recipes-commercial/common/revision.inc

RDEPENDS:${PN} = "bash"

S = "${WORKDIR}/test"

do_install() {
    install -d ${D}/home/root/test

    cp -rv ${B}/* ${D}/home/root/test
}

FILES:${PN} = "/home/root/test/*"

