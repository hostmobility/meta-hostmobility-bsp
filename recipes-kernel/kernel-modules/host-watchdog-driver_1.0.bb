SUMMARY = "Host watchdog driver"
DESCRIPTION = "${SUMMARY} for Host Monitor Platforms"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "5bc6679c0a33eac12b78dc2c3182990ae32d99ae"
SRCBRANCH = "watchdog-driver"

#SUBPATH="drivers/platform/host_monitor_cocpu"

inherit module
DEPENDS += "virtual/kernel"

S = "${WORKDIR}/git/drivers/platform/host_monitor_cocpu"

SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "host_watchdog"

KERNEL_MODULE_AUTOLOAD += "host_watchdog"