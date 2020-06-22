require u-boot-hostmobility_${PV}.inc
# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Copyright 2017-2019 NXP
# Copyright 2020 Host Mobility AB

DESCRIPTION = "i.MX U-Boot suppporting Host Mobility MX-5 boards"

DEPENDS_append = " bc-native dtc-native"

PROVIDES += "u-boot"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx5-pt)"
