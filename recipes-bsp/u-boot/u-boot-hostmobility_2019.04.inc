# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Copyright 2017-2019 NXP
# Copyright 2020 Host Mobility AB

inherit fsl-u-boot-localversion

DEFAULT_PREFERENCE_mx6 = "1"

DESCRIPTION = "i.MX U-Boot suppporting Host Mobility MX-5 boards"
require recipes-bsp/u-boot/u-boot.inc

DEPENDS += "flex-native bison-native bc-native"

FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-mobilityx-2019.04:"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

UBOOT_SRC = "git://git@gitlab.com/hostmobility/u-boot-mx5;protocol=ssh"
SRCBRANCH = "imx_v2019.04_4.19.35_1.0.0_mx5_bringup"
SRC_URI = "\
    ${UBOOT_SRC};branch=${SRCBRANCH} \
    file://0001-Add-target-to-generate-initial-environment.patch \
"
SRCREV = "56cb1bfc7992e7b0323bd1e5892cc6f42147f409"

S = "${WORKDIR}/git"

PV = "v2019.07+git${SRCPV}"
