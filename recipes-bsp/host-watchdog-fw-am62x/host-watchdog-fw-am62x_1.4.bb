SUMMARY = "rpmsg host watchdog firmware"
DESCRIPTION = "Cortex M4 ${SUMMARY} for Host Monitor Mini"
LICENSE = "BSD-3-Clause & MIT & MPL-2.0 & TI-TFL"

FILESEXTRAPATHS:prepend := "${THISDIR}/files/licenses:"

# Define the checksums for each license
LIC_FILES_CHKSUM = " \
    file://${WORKDIR}/COPYING;md5=5ff03a67270a4d692a8f09f87b04186f \
    file://${WORKDIR}/BSD-3-Clause;md5=b3990e3f8aef52635d5bb993f04f5c64 \
    file://${WORKDIR}/MIT;md5=937e6fa0ef05f0d9f908ab91a8426f12 \
    file://${WORKDIR}/TI-TFL;md5=8c34eb90d66683331330c490f789efea \
    file://${COMMON_LICENSE_DIR}/MPL-2.0;md5=815ca599c9df247a0c7f619bab123dad \
"

SRCREV = "78467cd8f8fab371391a3b2e7d7d253d75030c72"
SRCBRANCH = "main"

S = "${WORKDIR}"

SRC_URI += " \
    git://git@github.com/hostmobility/host-monitor-cocpu-fw.git;protocol=ssh;branch=${SRCBRANCH} \
    https://dr-download.ti.com/software-development/software-development-kit-sdk/MD-IIN1zFBAlS/11.02.00.23/mcu_plus_sdk_am62x_11_02_00_23-linux-x64-installer.run;name=mcu_plus_sdk_installer \
    https://dr-download.ti.com/software-development/ide-configuration-compiler-or-debugger/MD-nsUM6f7Vvb/1.24.2.4234/sysconfig-1.24.2_4234-setup.run;name=sysconfig_installer \
    https://dr-download.ti.com/software-development/ide-configuration-compiler-or-debugger/MD-ayxs93eZNN/4.0.1.LTS/ti_cgt_armllvm_4.0.1.LTS_linux-x64_installer.bin;name=armllvm_installer \
    file://COPYING \
    file://BSD-3-Clause \
    file://MIT \
    file://TI-TFL \
"

SRC_URI[mcu_plus_sdk_installer.sha256sum] = "08055f058c72ab1c94e8157a1b278ecfddfaa4dfb7c9d0a2b616bd10c57abd24"
SRC_URI[sysconfig_installer.sha256sum] = "a4cda2f624ed276e1eaec38fec2d0b0fd376b0fc8fafdd80fc01b6c533a14199"
SRC_URI[armllvm_installer.sha256sum] = "a7b34001e30f60c5dba219dca45f531512ee390b0946ad862df2d291ccea395f"

WD_BUILD_DIR = "${WORKDIR}/git/am62x/rpmsg_host_watchdog/am62x-sk/m4fss0-0_freertos/ti-arm-clang"

TI_DIR = "${WORKDIR}/ti"


do_configure() {
    mkdir -p ${TI_DIR}

    chmod +x ${DL_DIR}/mcu_plus_sdk_am62x_11_02_00_23-linux-x64-installer.run
    chmod +x ${DL_DIR}/sysconfig-1.24.2_4234-setup.run
    chmod +x ${DL_DIR}/ti_cgt_armllvm_4.0.1.LTS_linux-x64_installer.bin

    ${DL_DIR}/mcu_plus_sdk_am62x_11_02_00_23-linux-x64-installer.run --mode unattended --prefix ${TI_DIR}
    ${DL_DIR}/sysconfig-1.24.2_4234-setup.run --mode unattended --prefix "${TI_DIR}/sysconfig_1.24.2"
    ${DL_DIR}/ti_cgt_armllvm_4.0.1.LTS_linux-x64_installer.bin --mode unattended  --prefix ${TI_DIR}
}

do_compile() {
    export MCU_PLUS_SDK_PATH="${TI_DIR}/mcu_plus_sdk_am62x_11_02_00_23/"
    export TOOLS_PATH=${TI_DIR}
    cd ${WD_BUILD_DIR}
    make
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${WD_BUILD_DIR}/rpmsg_host_watchdog.mcu-m4f0_0.release.strip.out ${D}${nonarch_base_libdir}/firmware/am62-mcu-m4f0_0-fw
}

FILES:${PN} = "\
    ${nonarch_base_libdir}/firmware/am62-mcu-m4f0_0-fw \
"

INSANE_SKIP:${PN} += "arch"
