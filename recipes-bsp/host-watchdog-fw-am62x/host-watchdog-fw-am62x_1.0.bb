SUMMARY = "rpmsg host watchdog firmware"
DESCRIPTION = "Cortex M4 ${SUMMARY} for Host Monitor Mini"
LICENSE = "CLOSED"
# LICENSE = "GPL-2.0-or-later"
# LIC_FILES_CHKSUM = "file://${WORKDIR}/git/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV = "f1df0b9c087afb3a15fa1666db772c52eeec44c4"
SRCBRANCH = "as/dev/extract-wd-from-sdk"

S = "${WORKDIR}"

SRC_URI += " \
    gitsm://git@gitlab.com/hostmobility/host-monitor-am62-firmware.git;protocol=ssh;branch=${SRCBRANCH} \
    https://dr-download.ti.com/software-development/software-development-kit-sdk/MD-IIN1zFBAlS/09.02.01.06/mcu_plus_sdk_am62x_09_02_01_06-linux-x64-installer.run;name=mcu_plus_sdk_installer \
    https://dr-download.ti.com/software-development/ide-configuration-compiler-or-debugger/MD-nsUM6f7Vvb/1.20.0.3587/sysconfig-1.20.0_3587-setup.run;name=sysconfig_installer \
    https://dr-download.ti.com/software-development/ide-configuration-compiler-or-debugger/MD-ayxs93eZNN/3.2.2.LTS/ti_cgt_armllvm_3.2.2.LTS_linux-x64_installer.bin;name=armllvm_installer \
"

SRC_URI[mcu_plus_sdk_installer.sha256sum] = "1814c98e3f7d5a9e6570a464ef9a5db0b38c7b46a36532f82463896dbe21fea6"
SRC_URI[sysconfig_installer.sha256sum] = "983a2aa6677d6dadc14942a8f3e3b1fbe9c3a36269fd46ea259dfa499b97c373"
SRC_URI[armllvm_installer.sha256sum] = "2864bb1013ec60b1290f92ff723a4fc222653db1278c08ad81af6063f698dec9"

WD_BUILD_DIR = "${WORKDIR}/git/ti-am62/rpmsg_host_watchdog/am62x-sk/m4fss0-0_freertos/ti-arm-clang"

TI_DIR = "${WORKDIR}/ti"

do_configure() {
    mkdir -p ${TI_DIR}

    chmod +x ${DL_DIR}/mcu_plus_sdk_am62x_09_02_01_06-linux-x64-installer.run
    chmod +x ${DL_DIR}/sysconfig-1.20.0_3587-setup.run
    chmod +x ${DL_DIR}/ti_cgt_armllvm_3.2.2.LTS_linux-x64_installer.bin

    ${DL_DIR}/mcu_plus_sdk_am62x_09_02_01_06-linux-x64-installer.run --mode unattended --prefix ${TI_DIR}
    ${DL_DIR}/sysconfig-1.20.0_3587-setup.run --mode unattended --prefix "${TI_DIR}/sysconfig_1.20.0"
    ${DL_DIR}/ti_cgt_armllvm_3.2.2.LTS_linux-x64_installer.bin --mode unattended  --prefix ${TI_DIR}
}

do_compile() {
    export MCU_PLUS_SDK_PATH="${TI_DIR}/mcu_plus_sdk_am62x_09_02_01_06/"
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

PACKAGE_ARCH = "all"
INSANE_SKIP:${PN} += "arch"
