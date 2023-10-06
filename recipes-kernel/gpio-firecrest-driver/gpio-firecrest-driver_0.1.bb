SUMMARY = "Firecrest driver"
DESCRIPTION = "${SUMMARY}"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"
DRIVERBRANCH = "main"
SRCREV = "${AUTOREV}"

inherit module
DEPENDS += "virtual/kernel"

SRC_URI = "git://github.com/hostmobility/hm-commercial.git;protocol=git;branch=${SRCREV}"
S = "${WORKDIR}/git/drivers/gpio/gpio-firecrest"


SRC_URI = "git://git@github.com/hostmobility/hm-commercial.git;branch=${DRIVERBRANCH};protocol=ssh"

RPROVIDES:${PN} += "kernel-module-gpio-firecrest"
