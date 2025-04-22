SUMMARY = "gpio overlay"
DESCRIPTION = "${SUMMARY} for Mobility MXV"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "978cfb6eda18a108746b4bcce8aba800ed90fd5a"
SRCBRANCH = "main"

inherit module
DEPENDS += "virtual/kernel"

PR = "r2"

S = "${WORKDIR}/git/drivers/gpio/gpio-overlay"


SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "gpio_overlay"

KERNEL_MODULE_AUTOLOAD += "gpio_overlay"