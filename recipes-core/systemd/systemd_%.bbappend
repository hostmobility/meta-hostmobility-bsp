FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:imx8mp-var-dart = "file://systemd-poweroff.service"

SRC_URI:append:verdin-am62-hmm = "  \
            file://systemd-poweroff.service \
            file://wait-for-network.service \
"

do_install:append:imx8mp-var-dart() {
    install -Dm 0644 ${WORKDIR}/systemd-poweroff.service ${D}${systemd_system_unitdir}/systemd-poweroff.service
}

do_install:append:verdin-am62-hmm() {
    install -Dm 0644 ${WORKDIR}/systemd-poweroff.service ${D}${systemd_system_unitdir}/systemd-poweroff.service
    install -Dm 0644 ${WORKDIR}/wait-for-network.service ${D}${systemd_system_unitdir}/wait-for-network.service
}