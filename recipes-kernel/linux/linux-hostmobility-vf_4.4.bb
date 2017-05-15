require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

SUMMARY = "Linux kernel for MX-4 products using Toradex Colibri VFxx COMs"

SRC_URI = "git://github.com/hostmobility/linux-toradex.git;protocol=https;branch=${SRCBRANCH} \
           file://defconfig"

KERNEL_MODULE_AUTOLOAD += "${@bb.utils.contains('COMBINED_FEATURES', 'usbgadget', ' libcomposite', '',d)}"

LOCALVERSION = "-HEAD"
SRCBRANCH = "hm_vf_4.4"
SRCREV = "33fac6437474670595aaeca085e4fcbf87a8f665"
DEPENDS += "lzop-native bc-native"
COMPATIBLE_MACHINE = "(mx4_c61|mx4_v61)"
