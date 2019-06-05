# Provides common Tegra3 SoC settings on MX-4 products

MACHINEOVERRIDES =. "tegra:tegra3:"

# meta-angstrom/conf/distro/include/arm-defaults.inc
DEFAULTTUNE = "armv7athf-neon"
require conf/machine/include/tune-cortexa9.inc

PREFERRED_PROVIDER_u-boot ?= "u-boot-hostmobility-mainline"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-hostmobility_2016.11"
PREFERRED_PROVIDER_u-boot-fw-utils ?= "u-boot-hostmobility-fw-utils_2016.11"

IMAGE_FSTYPES = "tar.xz"

"