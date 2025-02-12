SUMMARY = "mx4-test"
DESCRIPTION = "Collection of scripts useful when testing stuff on MX-4"

SUBPATH = "test/"

INSANE_SKIP:${PN} += "ldflags"

require recipes-mx4-commercial/common/revision.inc

RDEPENDS:${PN} = "bash"

S = "${WORKDIR}/test"

do_install() {
    install -d ${D}/opt/hm/
    install -d ${D}/opt/hm/test

    cp -rv ${B}/* ${D}/opt/hm/test
}

FILES:${PN} = "/opt/hm/test/*"

#INSANE_SKIP = "32bit-time" fix WARNING: mx4-test-git-r0 do_package_qa: QA Issue: /opt/hm/test/can_test/cansequence uses 32-bit api 'setsockopt'
# This is only for mx4 which is 32-bits architecture. 
INSANE_SKIP = "32bit-time"
