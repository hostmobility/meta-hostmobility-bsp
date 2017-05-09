SUMMARY = "mx4-test"
DESCRIPTION = "Collection of scripts useful when testing stuff on MX-4"

SUBPATH = "test/"

require recipes-commercial/common/revision.inc

RDEPENDS_${PN} = "bash"

S = "${WORKDIR}/test"

do_install() {
    install -d ${D}/opt/hm/test

    cp -rv ${B}/* ${D}/opt/hm/test
}

FILES_${PN} = "/opt/hm/test/*"

