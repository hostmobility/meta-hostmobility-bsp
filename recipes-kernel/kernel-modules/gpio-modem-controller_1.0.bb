SUMMARY = "Modem gpio controller"
DESCRIPTION = "${SUMMARY} for Host Monitor Platforms which use EG modems from Quectel"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "a08e51294a515faf7d8dcd24334fa43c2a1bb431"
SRCBRANCH = "main"

inherit module
DEPENDS += "virtual/kernel"

S = "${WORKDIR}/git/drivers/gpio/modem_controller"

SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "modem_controller"

KERNEL_MODULE_AUTOLOAD += "modem_controller"