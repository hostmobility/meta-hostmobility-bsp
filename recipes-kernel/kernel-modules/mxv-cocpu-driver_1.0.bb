SUMMARY = "Host watchdog driver"
DESCRIPTION = "${SUMMARY} for Host Monitor Platforms"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "285ec663e231c846c5e1177d7a22dc262a9c6f01"
SRCBRANCH = "fix-mxv-driver-build-6.6-kernel-fslc"

inherit module
DEPENDS += "virtual/kernel"

S = "${WORKDIR}/git/drivers/platform/mx5pt_cocpu_driver"

SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "mx5-driver"

KERNEL_MODULE_AUTOLOAD += "mx5-driver"