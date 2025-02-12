SUMMARY = "Host Monitor commercial package group"
DESCRIPTION = "This brings in board support packages that are either proprietary or available to paying customers only"

inherit packagegroup

PACKAGES = "\
    packagegroup-host-monitor-commercial \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    hm-platform-version \
    flash-script \
    hm-autostart \
    mdio-netlink \
    mdio-tools \
    ${@bb.utils.contains('MACHINE_FEATURES', 'quectel_modem', 'qfirehose', '', d)} \
"
