# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Copyright 2017-2019 NXP
# Copyright 2020 Host Mobility AB

DESCRIPTION = "i.MX U-Boot suppporting Host Mobility MX-5 boards"
require recipes-bsp/u-boot/u-boot.inc

DEPENDS += "flex-native bison-native bc-native"

PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

UBOOT_SRC = "git://git@gitlab.com/hostmobility/u-boot-mx5;protocol=ssh"
SRCBRANCH = "fix-regulators"
SRC_URI = "\
    ${UBOOT_SRC};branch=${SRCBRANCH} \
    file://0001-Add-target-to-generate-initial-environment.patch \
"
SRCREV = "360acfe1932b2bfd8f51e3c95f8bf6b908d1dc32"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

LOCALVERSION ?= "u-boot-mx-${SRCBRANCH}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx5-pt)"
