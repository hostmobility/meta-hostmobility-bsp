SUMMARY = "Host watchdog driver"
DESCRIPTION = "${SUMMARY} for Host Monitor X"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "dbee158d3540913c0f93b04e88e0191ba74ce896"
SRCBRANCH = "main"

#SUBPATH="drivers/platform/host_monitor_x_cocpu"

inherit module
DEPENDS += "virtual/kernel"

S = "${WORKDIR}/git/drivers/platform/host_monitor_x_cocpu"

SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "imx_rpmsg_host_watchdog"

KERNEL_MODULE_AUTOLOAD += "imx_rpmsg_host_watchdog"