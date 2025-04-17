# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Copyright 2017-2019 NXP
# Copyright 2020 Host Mobility AB

DEFAULT_PREFERENCE_mx6 = "1"

DESCRIPTION = "i.MX U-Boot supporting Host Mobility MX-5 boards"
require recipes-bsp/u-boot/u-boot.inc

DEPENDS += "flex-native bison-native bc-native dtc-native"

PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

UBOOT_SRC = "git://source.codeaurora.org/external/imx/uboot-imx.git;protocol=https;"
SRCBRANCH = "master"
SRC_URI = "\
    ${UBOOT_SRC};branch=${SRCBRANCH} \
    file://0001-Add-target-to-generate-initial-environment.patch \
"

SRCREV = "85bdcc798163f72a7dfd723af4f0ef35d526ae09"

SRC_URI:append:mxv-base = " \
    file://0001-Add-mx5-defconfig.patch \
    file://0002-Add-mx5-device-tree-files.patch\
    file://0003-Add-mx5-uboot-and-spl-implementation.patch \
    file://0004-Modify-Makfile-and-kconfig-to-include-mx5-dtb-and-board-target.patch \
    file://0005-Let-U-Boot-know-that-MMC2-is-SD-card.patch \
" 

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

LOCALVERSION ?= "${SRCBRANCH}"
PV = "+git${SRCPV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

