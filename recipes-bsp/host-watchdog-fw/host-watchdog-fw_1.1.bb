SUMMARY = "rpmsg lite host watchdog firmware"
DESCRIPTION = "Cortex M4/M7 Firmware ${SUMMARY} for Host Monitor X"
LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=1ebbd3e34237af26da5dc08a4e440464"

SRCREV = "0206cfd8f27f5349af877e3de63a163a379638e3"
SRCBRANCH = "main"

DEPENDS += "cmake-native"

S = "${WORKDIR}/git/imx8mp-var-dart/rpmsg_lite_host_watchdog"

SRC_URI += " \
    git://git@github.com/hostmobility/host-monitor-cocpu-fw.git;protocol=ssh;branch=${SRCBRANCH} \
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