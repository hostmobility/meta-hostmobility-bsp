
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-mobility-imx:"

KERNEL_SRC = "git://github.com/nxp-imx/linux-imx.git;protocol=https;branch=${SRCBRANCH};name=linuxkernel"

DRIVERBRANCH = "kirkstone"
SRCREV_FORMAT = "linuxkernel_hmcommercial"
SRCREV_hmcommercial = "${AUTOREV}"

SRC_URI:append:mx5-pt= " \
    file://mxv_delta_imx_v7_defconfig.cfg \
    file://0006-Add-device-tree-for-mx5-pt.patch \
    file://0007-imx6qdl-mx-v-hdmi-display-dtsi-file-for-fsl-imx-linux.patch \
    file://0002-Add-support-on-USB-for-EG25-modem.patch \
    git://git@github.com/hostmobility/hm-commercial.git;name=hmcommercial;branch=${DRIVERBRANCH};subpath=drivers;protocol=ssh;destsuffix=git/drivers \
"

# Note defconfig file for Mxv-pt it will use sources/meta-freescale/recipes-kernel/linux/linux-imx/imx-nxp-bsp/defconfig
# which is identical with imx_v7_defconfig and we append our customisation with mxv_delta_imx_v7_defconfig 
# for mainline builds we use our defconfig under recipes-kernel/linux/linux-mobility-imx/mx5-pt/defconfig where mxv_delta_imx_v7_defconfig is already built in.

unset KBUILD_DEFCONFIG
DELTA_KERNEL_DEFCONFIG:mx5-pt = "mxv_delta_imx_v7_defconfig.cfg"

do_patch:append:mx5-pt() {
    # Add our drivers to the Makefiles to build them.
    echo 'obj-$(CONFIG_GPIO_NCV7751) += ncv7751-gpio-driver/gpio-ncv7751.o' >> '${S}/drivers/gpio/Makefile'
    echo 'obj-$(CONFIG_MX5_GPIO_OVERLAY) += gpio-overlay/gpio-overlay.o' >> '${S}/drivers/gpio/Makefile'
    echo 'obj-$(CONFIG_MX5_MODEM_DRIVER) += modem_controller/modem_controller.o' >> '${S}/drivers/gpio/Makefile'
    echo 'obj-$(CONFIG_MX5_COCPU) += mx5pt_cocpu_driver/' >> '${S}/drivers/platform/Makefile'

    echo 'source "drivers/platform/mx5pt_cocpu_driver/Kconfig"' >>'${S}/drivers/platform/Kconfig'
    echo 'source "drivers/gpio/gpio-overlay/Kconfig"' >>'${S}/drivers/gpio/Kconfig'
    echo 'source "drivers/gpio/ncv7751-gpio-driver/Kconfig"' >>'${S}/drivers/gpio/Kconfig'
    echo 'source "drivers/gpio/modem_controller/Kconfig"' >>'${S}/drivers/gpio/Kconfig'
}