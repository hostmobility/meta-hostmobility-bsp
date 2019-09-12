FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://minirc.dfl"

do_install_append() {
    install -d ${D}/${sysconfdir}/
    install -m 644 ${WORKDIR}/minirc.dfl ${D}${sysconfdir}/minirc.dfl
}
