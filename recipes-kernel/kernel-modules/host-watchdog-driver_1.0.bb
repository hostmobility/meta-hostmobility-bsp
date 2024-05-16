SUMMARY = "Host watchdog driver"
DESCRIPTION = "${SUMMARY} for Host Monitor X"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "90cc1162e4af0e65a668eae1e445f95b59c803c6"
SRCBRANCH = "hmx-driver"
#SRCBRANCH = "main"

#SUBPATH="drivers/platform/host_monitor_x_cocpu"

inherit module
DEPENDS += "virtual/kernel"

S = "${WORKDIR}/git/drivers/platform/host_monitor_x_cocpu"

SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "imx_rpmsg_host_watchdog"

KERNEL_MODULE_AUTOLOAD += "imx_rpmsg_host_watchdog"