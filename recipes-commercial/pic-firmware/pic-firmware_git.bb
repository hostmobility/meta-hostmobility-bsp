SUMMARY = "pic-firmware"
DESCRIPTION = "Firmware files for CO-CPU on MX-4"

SUBPATH = "pic-firmware"

require recipes-commercial/common/revision.inc

S = "${WORKDIR}/pic-firmware"

python do_patch() {
    import glob   
    import os.path

    filepath = d.getVar('B')
    pic_bootloader = d.getVar('MX4_PIC_BOOTLOADER')
    pic_app = d.getVar('MX4_PIC_APP')
    pic_backupapp = d.getVar('MX4_PIC_BACKUPAPP')

    os.rename(
        glob.glob(filepath + "/" + pic_app + "-app-*.hex")[0],
        os.path.join(d.getVar('B'), "app.hex")
        )

    os.rename(
        glob.glob(filepath + "/" + pic_backupapp + "-bl-app-*.hex")[0],
        os.path.join(d.getVar('B'), "bl-app.hex")
        )

    os.rename(
        glob.glob(filepath + "/" + pic_bootloader + '-bl-?.*.hex')[0],
        os.path.join(d.getVar('B'), "bl.hex")
        )
}

do_install() {
    install -d ${D}/opt/hm/pic

    install -m 744 ${B}/bl.hex ${D}/opt/hm/pic
    install -m 744 ${B}/bl-app.hex ${D}/opt/hm/pic
    install -m 744 ${B}/app.hex ${D}/opt/hm/pic

}

FILES:${PN} = "/opt/hm/pic/*.hex"

