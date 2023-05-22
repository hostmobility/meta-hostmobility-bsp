SUMMARY = "Legacy Network Names Script and Service"
DESCRIPTION = "A script to handle legacy network names"
LICENSE = "CLOSED"
PR = "r0"

SRC_URI = "\
	file://hm-stable-names.sh \
        "

S = "${WORKDIR}"


RDEPENDS:${PN} = "bash"


do_install() {
    install -d ${D}/opt/hm
    install -m 0755 ${WORKDIR}/hm-stable-names.sh ${D}/opt/hm/
}

FILES:${PN} += "\
    /opt/hm/hm-stable-names.sh \
"

RPROVIDES:${PN} = "hm-stable-names"



