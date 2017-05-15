# Provides common Tegra3 SoC settings on MX-4 products

MACHINEOVERRIDES =. "tegra:tegra3:"

UBOOT_ENTRYPOINT = "0x82008000"
UBOOT_LOADADDRESS = "${UBOOT_ENTRYPOINT}"

# meta-angstrom/conf/distro/include/arm-defaults.inc
DEFAULTTUNE = "armv7athf-neon"
require conf/machine/include/tune-cortexa9.inc
