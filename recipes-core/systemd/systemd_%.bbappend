FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:imx8mp-var-dart = " file://imx.conf \
            file://0001-units-add-dependencies-to-avoid-conflict-between-con.patch \
            file://0002-units-disable-systemd-networkd-wait-online-if-Networ.patch \
"

do_install:append:imx8mp-var-dart() {
    install -Dm 0644 ${WORKDIR}/imx.conf ${D}${sysconfdir}/systemd/logind.conf.d/imx.conf
}
