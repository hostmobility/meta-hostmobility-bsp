FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRCBRANCH = "lf-6.18.y_6.18.20-2.0.0_var01"
SRCREV = "e56b4e4a25bb2fb3252f469ab879139e8351eddb"

LINUX_VERSION = "6.18.20"
PV = "${LINUX_VERSION}+git"
LINUX_VERSION_EXTENSION = "-var-lts-next"

SRC_URI = " \
    ${KERNEL_SRC};branch=${SRCBRANCH} \
    file://defconfig \
"

KBUILD_DEFCONFIG:imx8mp-var-dart-hmx1 = ""

COMPATIBLE_MACHINE = "(imx8mp-var-dart-hmx1)"

# Temporary while porting 6.18 on older Yocto/kernel tooling.
KMETA_AUDIT = ""
do_kernel_configcheck[noexec] = "1"
