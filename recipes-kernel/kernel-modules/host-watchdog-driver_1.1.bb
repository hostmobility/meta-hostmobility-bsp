SUMMARY = "Host watchdog driver"
DESCRIPTION = "${SUMMARY} for Host Monitor Platforms"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "ba6d1ee8bf97b195e1b15ff65825981baed2613c"
SRCBRANCH = "host-watchdog-adapt-6.6-kernel"

#SUBPATH="drivers/platform/host_monitor_cocpu"

inherit module
DEPENDS += "virtual/kernel"

S = "${WORKDIR}/git/drivers/platform/host_monitor_cocpu"

SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "host_watchdog"

KERNEL_MODULE_AUTOLOAD += "host_watchdog"