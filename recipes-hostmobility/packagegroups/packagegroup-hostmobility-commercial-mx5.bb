SUMMARY = "MX-5 Commercial package group"
DESCRIPTION = "Package group bringing in packages that are closed source and part of BSP"

inherit packagegroup

PACKAGES = "\
    packagegroup-hostmobility-commercial-mx5 \
    "

ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} += "\
    mx5-platform-version \
    ${@bb.utils.contains('MACHINE_FEATURES', 'j1708', 'j1708-lib j1708-test', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'hm-cocpu_updater', 'mx5-hm-cocpu-updater', '', d)} \
"
