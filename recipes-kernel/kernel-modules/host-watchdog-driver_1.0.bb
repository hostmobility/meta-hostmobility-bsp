SUMMARY = "Host watchdog driver"
DESCRIPTION = "${SUMMARY} for host monitor x"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "38bcb61098388f9bb9be3a5063960054eab081c9"
SRCBRANCH = "hmx-driver"
#SRCBRANCH = "main"

#SUBPATH="drivers/platform/host_monitor_x_cocpu"

inherit module
DEPENDS += "virtual/kernel"

S = "${WORKDIR}/git/drivers/platform/host_monitor_x_cocpu"

SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "imx_rpmsg_host_watchdog"

KERNEL_MODULE_AUTOLOAD += "imx_rpmsg_host_watchdog"