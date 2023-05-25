SUMMARY = "Legacy Network Names Renaming Script"

DESCRIPTION = "\
	       A bash script (/opt/hm/hm-stable-names.sh) intended to be run \
	       after the interfaces have been found but before network \
	       interfaces are brought up. The script renames ethX and canX \
	       interfaces so they always appear at the same physical connection \
	       "
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

COMPATIBLE_MACHINE = "(imx8mp-var-dart-hmx1)"



