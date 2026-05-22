SUMMARY = "Modem gpio controller"
DESCRIPTION = "${SUMMARY} for Host Monitor Platforms which use EG modems from Quectel"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "5a48691860217850f51a59953cb42671cf545243"
SRCBRANCH = "main"

inherit module
DEPENDS += "virtual/kernel"

PR = "r2"

S = "${WORKDIR}/git/drivers/gpio/modem_controller"

SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "modem_controller"

KERNEL_MODULE_AUTOLOAD += "modem_controller"