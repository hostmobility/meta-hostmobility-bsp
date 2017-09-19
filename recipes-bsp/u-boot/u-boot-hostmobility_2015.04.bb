include u-boot-hostmobility.inc

LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"

PV = "v${SRCBRANCH}+git${SRCPV}"

SRCREV = "735925d807abc3cd64b89038e7aff1245d41b7e4"
SRCBRANCH = "2015.04-hm"

# require u-boot-dtb-tegra.bin to be used
UBOOT_IMAGE_tegra = "u-boot-dtb-tegra-${MACHINE}-${PV}-${PR}.${UBOOT_SUFFIX}"
UBOOT_BINARY_tegra = "u-boot-dtb-tegra.${UBOOT_SUFFIX}"
UBOOT_SYMLINK_tegra = "u-boot-dtb-tegra-${MACHINE}.${UBOOT_SUFFIX}"
