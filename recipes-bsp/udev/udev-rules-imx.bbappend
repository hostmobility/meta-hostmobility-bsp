FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

RDEPENDS:${PN}:append:imx8mp-var-dart = " bash iproute2"

SRC_URI:append:imx8mp-var-dart = " \
    file://10-hm-temp-names.rules \
    file://99-hm-interface-rename.rules \
    file://hm-rename-interface.sh \
"


do_install:append:imx8mp-var-dart () {
    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 0644 ${WORKDIR}/10-hm-temp-names.rules ${D}${sysconfdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/99-hm-interface-rename.rules ${D}${sysconfdir}/udev/rules.d/

    install -d ${D}/opt/hm
    install -m 0755 ${WORKDIR}/hm-rename-interface.sh ${D}/opt/hm/
}

FILES:${PN}:append:imx8mp-var-dart = " /opt/hm/hm-rename-interface.sh"
