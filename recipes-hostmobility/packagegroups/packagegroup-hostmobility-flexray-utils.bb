SUMMARY = "MX-4 flexray utils package group"
DESCRIPTION = "Package group bringing in packages that are closed source and part of BSP"

inherit packagegroup

PACKAGES = "\
    packagegroup-hostmobility-flexray-utils \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    flexray \
    flexrayctl \
    flexrayd \
    flexraydump \
    flexraytimesync \
    flexraywd \
    minicom \
    fr-mcu \
    flexray-firmware \
"
