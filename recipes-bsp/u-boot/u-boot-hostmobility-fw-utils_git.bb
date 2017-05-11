SUMMARY = "U-boot bootloader fw_printenv/setenv utils"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"
SECTION = "bootloader"
PROVIDES = "u-boot-fw-utils"
RPROVIDES_${PN} = "u-boot-fw-utils"
DEPENDS = "mtd-utils"

COMPATIBLE_MACHINE = "(mx4_v61|mx4_c61)"
DEFAULT_PREFERENCE_mx4_v61 = "1"
DEFAULT_PREFERENCE_mx4_c61 = "1"


FILESPATHPKG =. "git:"

SRCREV = "68d62aa5688ca944cbcdde163ca5c54b9379dce6"
SRCBRANCH = "2015.04-hm"
SRC_URI = " \
    git://git@github.com/hostmobility/u-boot-toradex.git;protocol=ssh;branch=${SRCBRANCH} \
    file://fw_env.config \
"

PV = "v2015.04-hm+git${SRCPV}"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" STRIP="${STRIP}"'

INSANE_SKIP_${PN} = "already-stripped ldflags"

inherit pkgconfig uboot-config

do_compile () {
    oe_runmake ${UBOOT_MACHINE}
    oe_runmake env
}

do_install () {
    install -d ${D}${base_sbindir} ${D}${sysconfdir}
    install -m 755 ${S}/tools/env/fw_printenv ${D}${base_sbindir}/fw_printenv
    ln -s fw_printenv ${D}${base_sbindir}/fw_setenv
    install -m 644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/
}

pkg_postinst_${PN}_tegra2 () {
    # can't do this offline
    if [ "x$D" != "x" ]; then
        exit 1
    fi
    grep u-boot-env /proc/mtd | awk '{print "/dev/" substr($1,0,4) " 0x00000000 0x00010000 0x" $3 " 1" >> "/etc/fw_env.config" }'
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
