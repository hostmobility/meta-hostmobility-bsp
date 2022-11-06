
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-mobility-imx:"

SRC_URI:append:mx5-pt= " \
    file://0006-Add-device-tree-for-mx5-pt.patch \
"
