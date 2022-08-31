# Provides common Tegra2 SoC settings on MX-4 products

MACHINEOVERRIDES =. "tegra:tegra2:"

UBOOT_ENTRYPOINT = "0x00008000"

IMAGE_FSTYPES:append = " ubifs"

# Enable free --space-fixup (-F) by default
MKUBIFS_ARGS = " -c 4084 -e 248KiB -m 4KiB -F"
UBINIZE_ARGS = " -p 256KiB -m 4KiB -s 4KiB"
UBI_VOLNAME = "rootfs"

# T20 does not have neon
DEFAULTTUNE = "armv7athf"
#choose a specific vfp, or we end up with the default vfpv2 optimization
TUNE_CCARGS += "-mfpu=vfpv3-d16"
require conf/machine/include/tune-cortexa9.inc

