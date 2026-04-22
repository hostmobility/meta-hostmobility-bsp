SUMMARY = "rpmsg lite host watchdog firmware"
DESCRIPTION = "Cortex M7 ${SUMMARY} for Host Monitor X"
LICENSE = "BSD-3-Clause & MIT & Apache-2.0 & MPL-2.0"

FILESEXTRAPATHS:prepend := "${THISDIR}/files/licenses:"

# Define the checksums for each license
LIC_FILES_CHKSUM = " \
    file://${WORKDIR}/COPYING;md5=fca6e36746fbb26f9a2d861033492ce6 \
    file://${WORKDIR}/BSD-3-Clause;md5=5f288896623d08808efa3b96fb43e1cb \
    file://${WORKDIR}/MIT;md5=947e48c195ca27ee1dda03b9ef840573 \
    file://${WORKDIR}/CMSIS-NOTICE;md5=354769e8a400ec4eaebd8b9fe7c1ccdc \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://${COMMON_LICENSE_DIR}/MPL-2.0;md5=815ca599c9df247a0c7f619bab123dad \
"

SRCREV = "05e8c9036aca7cdc76455eef30225217efb4051d"
SRCBRANCH = "main"

DEPENDS += "cmake-native"

S = "${WORKDIR}/git/imx8mp-var-dart/rpmsg_lite_host_watchdog"

SRC_URI += " \
    git://git@github.com/hostmobility/host-monitor-cocpu-fw.git;protocol=ssh;branch=${SRCBRANCH} \
    file://COPYING \
    file://BSD-3-Clause \
    file://MIT \
    file://CMSIS-NOTICE \
    https://developer.arm.com/-/media/Files/downloads/gnu/13.2.rel1/binrel/arm-gnu-toolchain-13.2.rel1-x86_64-arm-none-eabi.tar.xz;name=arm-gnu-toolchain-13.2.rel1-x86_64-arm-none-eabi \
"

SRC_URI[arm-gnu-toolchain-13.2.rel1-x86_64-arm-none-eabi.sha256sum] = "6cd1bbc1d9ae57312bcd169ae283153a9572bd6a8e4eeae2fedfbc33b115fdbb"

CM_GCC = "arm-gnu-toolchain-13.2.Rel1-x86_64-arm-none-eabi"

do_compile() {
    # Configure ARMGCC
    export ARMGCC_DIR="${WORKDIR}/${CM_GCC}"
    export HMX=1
    cd "${S}"
    ./clean.sh
    CFLAGS="" CXXFLAGS="" ./build_ddr_release.sh
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/ddr_release/rpmsg_lite_host_watchdog.bin ${D}${nonarch_base_libdir}/firmware/rpmsg_lite_host_watchdog.bin
}
FILES:${PN} = "\
    ${nonarch_base_libdir}/firmware/rpmsg_lite_host_watchdog.bin \
"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
