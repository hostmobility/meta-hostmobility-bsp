FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:class-target = " file://fw_env.config"

do_install:append:class-target() {
	install -d ${D}${sysconfdir}
	install -m 644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}

}

FILES:${PN}:append:class-target = " ${sysconfdir}"

do_install:append:tegra3() {
    install -d ${D}${sysconfdir}/profile.d/
    install -m 0644 ${WORKDIR}/fw_unlock_mmc.sh ${D}${sysconfdir}/profile.d/fw_unlock_mmc.sh
    ln -s fw_printenv ${D}/opt/hm/fw_env/fw_setenv
}
do_install:append:vf60() {
    ln -s fw_printenv ${D}/opt/hm/fw_env/fw_setenv
}