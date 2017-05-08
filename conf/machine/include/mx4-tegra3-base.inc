# Provides common Tegra2 SoC settings on MX-4 products

MACHINEOVERRIDES =. "tegra:tegra3:"

UBOOT_ENTRYPOINT = "0x82008000"
UBOOT_LOADADDRESS = "${UBOOT_ENTRYPOINT}"

# T20 does not have neon
DEFAULTTUNE = "armv7athf"
#choose a specific vfp, or we end up with the default vfpv2 optimization
TUNE_CCARGS += "-mfpu=vfpv3-d16"
require conf/machine/include/tune-cortexa9.inc

