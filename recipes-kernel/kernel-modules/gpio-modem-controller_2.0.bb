SUMMARY = "Modem gpio controller"
DESCRIPTION = "${SUMMARY} for Host Monitor Platforms which use EG modems from Quectel"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "f9f7e53950ccc3176efdde3b3c66356730d3cadb"
SRCBRANCH = "main"

inherit module
DEPENDS += "virtual/kernel"

PR = "r2"

S = "${WORKDIR}/git/drivers/gpio/modem_controller"

SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "modem_controller"

KERNEL_MODULE_AUTOLOAD += "modem_controller"