FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:imx8mp-var-dart = " file://imx.conf \
            file://0001-units-add-dependencies-to-avoid-conflict-between-con.patch \
            file://0002-units-disable-systemd-networkd-wait-online-if-Networ.patch \
            file://systemd-poweroff.service \
            file://10-can0.link \
            file://11-can1.link \
            file://12-can2.link \
            file://13-can3.link \
            file://14-can4.link \
            file://15-can5.link \
            file://20-eth0.link \
            file://21-eth1.link \
            file://22-eth2.link \
            file://23-eth3.link \
"

SRC_URI:append:verdin-am62-hmm = "  \
            file://systemd-poweroff.service \
            file://wait-for-network.service \
"

do_install:append:imx8mp-var-dart() {
    install -d ${D}${sysconfdir}/systemd/network

    install -Dm 0644 ${WORKDIR}/imx.conf ${D}${sysconfdir}/systemd/logind.conf.d/imx.conf
    install -Dm 0644 ${WORKDIR}/systemd-poweroff.service ${D}${systemd_system_unitdir}/systemd-poweroff.service

    install -m 0644 ${WORKDIR}/10-can0.link ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/11-can1.link ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/12-can2.link ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/13-can3.link ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/14-can4.link ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/15-can5.link ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/20-eth0.link ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/21-eth1.link ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/22-eth2.link ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/23-eth3.link ${D}${sysconfdir}/systemd/network/
}

do_install:append:verdin-am62-hmm() {
    install -Dm 0644 ${WORKDIR}/systemd-poweroff.service ${D}${systemd_system_unitdir}/systemd-poweroff.service
    install -Dm 0644 ${WORKDIR}/wait-for-network.service ${D}${systemd_system_unitdir}/wait-for-network.service
}