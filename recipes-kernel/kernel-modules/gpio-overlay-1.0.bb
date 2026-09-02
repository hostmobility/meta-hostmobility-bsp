SUMMARY = "gpio overlay"
DESCRIPTION = "${SUMMARY} for Mobility MXV"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "529beab490104e4e7d8e90ba980af37387c7cd1f"
SRCBRANCH = "main"

inherit module
DEPENDS += "virtual/kernel"

PR = "r2"

S = "${WORKDIR}/git/drivers/gpio/gpio-overlay"


SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "gpio-overlay"

KERNEL_MODULE_AUTOLOAD += "gpio-overlay"