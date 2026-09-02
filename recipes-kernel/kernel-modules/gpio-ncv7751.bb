SUMMARY = "ncv7751 gpio driver"
DESCRIPTION = "${SUMMARY} for Mobility MXV"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "44d89223d1d109fca9c368f9da4a93ec4e105588"
SRCBRANCH = "main"

inherit module
DEPENDS += "virtual/kernel"

PR = "r2"

S = "${WORKDIR}/git/drivers/gpio/ncv7751-gpio-driver"


SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "gpio-ncv7751"

KERNEL_MODULE_AUTOLOAD += "gpio-ncv7751"