FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:imx8mp-var-dart = " file://imx.conf \
            file://0001-units-add-dependencies-to-avoid-conflict-between-con.patch \
            file://0002-units-disable-systemd-networkd-wait-online-if-Networ.patch \
            file://systemd-poweroff.service \
"

SRC_URI:append:verdin-am62-hmm = "  \
            file://systemd-poweroff.service \
            file://wait-for-network.service \
"

do_install:append:imx8mp-var-dart() {
    install -Dm 0644 ${WORKDIR}/imx.conf ${D}${sysconfdir}/systemd/logind.conf.d/imx.conf
    install -Dm 0644 ${WORKDIR}/systemd-poweroff.service ${D}${systemd_system_unitdir}/systemd-poweroff.service
}

do_install:append:verdin-am62-hmm() {
    install -Dm 0644 ${WORKDIR}/systemd-poweroff.service ${D}${systemd_system_unitdir}/systemd-poweroff.service
    install -Dm 0644 ${WORKDIR}/wait-for-network.service ${D}${systemd_system_unitdir}/wait-for-network.service
}