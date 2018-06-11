inherit kernel
require recipes-kernel/linux/linux-hostmobility.inc

LINUX_VERSION ?= "3.1.10"

PV = "${LINUX_VERSION}+gitr${SRCREV}"

S = "${WORKDIR}/git"

LOCALVERSION = "-${SRCBRANCH}"
SRC_URI = "git://github.com/hostmobility/linux-toradex.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "mx4-bsp-2.0.x-tegra"
SRCREV = "efae2114636c8dcf798582948aedef6d3a3884d7"

COMPATIBLE_MACHINE = "(mx4-t30|mx4-t20|mx4-ct|mx4-vcc|mx4-gtt|mx4-mil)"

# One possibiltiy for changes to the defconfig:
config_script () {
#    #example change to the .config
#    #sets CONFIG_TEGRA_CAMERA unconditionally to 'y'
#    sed -i -e /CONFIG_TEGRA_CAMERA/d ${B}/.config
#    echo "CONFIG_TEGRA_CAMERA=y" >> ${B}/.config
    echo "dummy" > /dev/null
}

do_configure_prepend () {
    #use the defconfig provided in the kernel source tree
    #assume its called ${MACHINE}_defconfig, but with '_' instead of '-'
    DEFCONFIG="`echo ${MACHINE} | sed -e 's/\-/\_/g' -e 's/$/_defconfig/'`"

    cd ${S}
    export KBUILD_OUTPUT=${B}
    oe_runmake $DEFCONFIG

    #maybe change some configuration
    config_script

    #Add Host Mobility BSP Version as LOCALVERSION
    sed -i -e /CONFIG_LOCALVERSION/d ${B}/.config
    echo "CONFIG_LOCALVERSION=\"${LOCALVERSION}\"" >> ${B}/.config

    #Add GIT revision to the local version
    head=`git --git-dir=${S}/.git rev-parse --verify --short HEAD 2> /dev/null`
    printf "%s%s" +g $head > ${S}/.scmversion

    cd - > /dev/null
}

do_uboot_mkimage_prepend () {
    cd ${B}
}

# glibc 2.24 set the oldest kernel to 3.2.0, however the downstream L4T 3.1.10
# kernel provides all needed interfaces, so override the check_oldest_kernel to
# disable the warning
python check_oldest_kernel() {
    oldest_kernel = d.getVar('OLDEST_KERNEL', True)
    kernel_version = "3.2.0"
    tclibc = d.getVar('TCLIBC', True)
    if tclibc == 'glibc':
        kernel_version = kernel_version.split('-', 1)[0]
        if oldest_kernel and kernel_version:
            if bb.utils.vercmp_string(kernel_version, oldest_kernel) < 0:
                bb.warn('%s: OLDEST_KERNEL is "%s" but the version of the kernel you are building is "%s" - therefore %s as built may not be compatible with this kernel. Either set OLDEST_KERNEL to an older version, or build a newer kernel.' %(d.getVar('PN', True), oldest_kernel, kernel_version, tclibc))
}
