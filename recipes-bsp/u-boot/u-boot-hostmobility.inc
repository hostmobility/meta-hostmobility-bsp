# Copyright (C) 2017 Host Mobility AB
# Based on u-boot-toradex.inc Copyright (C) 2015 Toradex AG
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-bsp/u-boot/u-boot.inc

inherit fsl-u-boot-localversion

SUMMARY = "U-Boot bootloader with support for MX-4 products from Host Mobility AB"
LICENSE = "GPLv2+"

PROVIDES += "u-boot"

LOCALVERSION = " ${SRCBRANCH}"

SRC_URI = "git://github.com/hostmobility/u-boot-toradex.git;protocol=https;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

# FIXME: Allow linking of 'tools' binaries with native libraries
#        used for generating the boot logo and other tools used
#        during the build process.
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS}" \
                 HOSTLDFLAGS="${BUILD_LDFLAGS}" \
                 HOSTSTRIP=true'

PACKAGE_ARCH = "${MACHINE_ARCH}"
