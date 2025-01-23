SUMMARY = "gpio overlay"
DESCRIPTION = "${SUMMARY} for Host Monitor Platforms"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "4491c035ffdb54d93ec6776bf91ae73484efcb0a"
SRCBRANCH = "main"

inherit module
DEPENDS += "virtual/kernel"

PR = "r2"

S = "${WORKDIR}/git/drivers/gpio/gpio_overlay_hmm"


SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "gpio_overlay_hmm"

KERNEL_MODULE_AUTOLOAD += "gpio_overlay_hmm"