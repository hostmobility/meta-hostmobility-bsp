SUMMARY = "MX-V Commercial package group"
DESCRIPTION = "Package group bringing in packages that are closed source and part of BSP"

inherit packagegroup

PACKAGES = "\
    packagegroup-hostmobility-commercial-mx5 \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    mx5-platform-version \
    ${@bb.utils.contains('MACHINE_FEATURES', 'j1708', 'j1708-lib j1708-test', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'hm-cocpu_updater', 'mx5-hm-cocpu-updater', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'hm-cocpu_updater-service', 'mx5-hm-cocpu-updater-service', '', d)} \
    qfirehose \
"
