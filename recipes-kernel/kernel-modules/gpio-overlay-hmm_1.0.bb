SUMMARY = "gpio overlay"
DESCRIPTION = "${SUMMARY} for Host Monitor Platforms"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "e581bf893f46f2ae3a51a53d8adc564cb742385b"
SRCBRANCH = "main"

inherit module
DEPENDS += "virtual/kernel"

PR = "r1"

S = "${WORKDIR}/git/drivers/gpio/gpio_overlay_hmm"


SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "gpio_overlay_hmm"

KERNEL_MODULE_AUTOLOAD += "gpio_overlay_hmm"