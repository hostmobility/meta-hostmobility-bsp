SUMMARY = "Host watchdog driver"
DESCRIPTION = "${SUMMARY} for Host Monitor Platforms"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=65dd37ccb3e888dc57e47d925b80b38a"
SRCREV = "f5b510395bcc80ca95889e59511a0e530b1e05b8"
SRCBRANCH = "main"

#SUBPATH="drivers/platform/host_monitor_cocpu"

inherit module
DEPENDS += "virtual/kernel"

S = "${WORKDIR}/git/drivers/platform/host_monitor_cocpu"

SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;protocol=ssh;branch=${SRCBRANCH}"

RPROVIDES:${PN} += "host_watchdog"

KERNEL_MODULE_AUTOLOAD += "host_watchdog"