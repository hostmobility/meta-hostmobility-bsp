SUMMARY = "Host watchdog driver"
DESCRIPTION = "${SUMMARY} for Host Monitor Platforms"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "6c109194723fbedd3719fb126cfabe0d8676f86f"
SRCBRANCH = "main"

#SUBPATH="drivers/platform/host_monitor_cocpu"

inherit module
DEPENDS += "virtual/kernel"

S = "${WORKDIR}/git/drivers/platform/host_monitor_cocpu"

SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "host_watchdog"

KERNEL_MODULE_AUTOLOAD += "host_watchdog"