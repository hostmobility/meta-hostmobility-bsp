SUMMARY = "Host watchdog driver"
DESCRIPTION = "${SUMMARY} for Host Monitor Platforms"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "6ec1ed76ff1ec559419c24e4bfa9756ecfad7216"
SRCBRANCH = "main"

inherit module
DEPENDS += "virtual/kernel"

S = "${WORKDIR}/git/drivers/platform/mx5pt_cocpu_driver"

SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "mxv-cocpu-driver"

KERNEL_MODULE_AUTOLOAD += "mxv-cocpu-driver"