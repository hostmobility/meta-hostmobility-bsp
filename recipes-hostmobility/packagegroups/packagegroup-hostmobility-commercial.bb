SUMMARY = "MX-4 Commercial package group"
DESCRIPTION = "Package group bringing in packages that are closed source and part of BSP"

inherit packagegroup

PACKAGES = "\
    packagegroup-hostmobility-commercial \
    "

ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} += "\
    pic-programmer \
    pic-firmware \
    \
    mx4-utils \
    mx4-utils-autostart \
    mx4-utils-scripts \
    mx4-utils-mount-config \
    \
    mx4-test \
    \
    can-xcvr \
    \
    gps-parser \
    \
    munch \
    \
    ${@bb.utils.contains('MACHINE_FEATURES', 'j1708', 'j1708-lib', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'kline', 'kline-lib', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'lin', 'lin-config', '', d)} \
"
