include u-boot-hostmobility.inc

PV = "v2015.04-hm+git${SRCPV}"

SRCREV = "735925d807abc3cd64b89038e7aff1245d41b7e4"
SRCBRANCH = "2015.04-hm"
COMPATIBLE_MACHINE = "(mx4_v61|mx4_c61|mx4-t20|mx4-t30|mx4-ct|mx4-vcc|mx4-gtt|mx4-mil)"

# require u-boot-dtb-tegra.bin to be used
UBOOT_IMAGE_tegra = "u-boot-dtb-tegra-${MACHINE}-${PV}-${PR}.${UBOOT_SUFFIX}"
UBOOT_BINARY_tegra = "u-boot-dtb-tegra.${UBOOT_SUFFIX}"
UBOOT_SYMLINK_tegra = "u-boot-dtb-tegra-${MACHINE}.${UBOOT_SUFFIX}"
