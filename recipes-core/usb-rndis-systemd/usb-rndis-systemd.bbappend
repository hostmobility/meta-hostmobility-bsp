FILESEXTRAPATHS_prepend := "${THISDIR}/files/:"

SRC_URI += " \
    file://usb-rndis.rules \
    file://udhcpd-usb-rndis.conf \
    file://usb-rndis-dhcp.service \
    file://udhcpd.leases \
"

do_install_append () {
    install -d ${D}/${sysconfdir}/udev/rules.d ${D}/${bindir}
    install -d ${D}/${sysconfdir}/systemd/system
    install -d ${D}/${localstatedir}/lib/misc

    install -m 0644 ${WORKDIR}/usb-rndis.rules ${D}/${sysconfdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/udhcpd-usb-rndis.conf ${D}/${sysconfdir}/
    install -m 0644 ${WORKDIR}/usb-rndis-dhcp.service ${D}/${sysconfdir}/systemd/system/usb-rndis-dhcp.service
    install -m 0644 ${WORKDIR}/udhcpd.leases ${D}/${localstatedir}/lib/misc/udhcpd.leases
}

FILES_${PN} += "/usr/bin/"
