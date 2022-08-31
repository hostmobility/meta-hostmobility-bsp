# Copyright (C) 2017 Host Mobility AB
SUMMARY = "Multicore communication kernel module"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

PROVIDES = "virtual/kernel-module-pic"
RPROVIDES:${PN} = "virtual/kernel-module-pic"
RPROVIDES:${PN}-dev = "virtual/kernel-module-mcc-pic"

inherit module

SRCREV = "33e14d626c6d73f35fb237d0392e6140a6dfd181"
SRC_URI = "git://github.com/hostmobility/mx4-kmod-pic.git;protocol=https;branch=master"

SRC_URI[md5sum] = "849dfdc34e08c7c82a5e8b452a95f1b3"
SRC_URI[sha256sum] = "ece0c9ccbfb5d2771b115f750361184bb80b2ae5fe82d97d38be2bfee3eeb87e"

S = "${WORKDIR}/git"

KERNEL_MODULE_AUTOLOAD += "mx4_pic"

