# Class to include some extra packages from Host Mobility layers

MACHINE_EXTRA_RDEPENDS_append = " \
    kernel-modules \
    kernel-module-pic \
    u-boot-hostmobility-fw-utils \
    ${@bb.utils.contains('LICENSE_FLAGS_WHITELIST', 'closed', \
                          'packagegroup-hostmobility-commercial', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'wifi', \
                          'linux-firmware-ralink linux-firmware-ath9k', '', d)} \
"

MACHINE_EXTRA_RDEPENDS_append_tegra3 = " \
    e2fsprogs-resize2fs \
"

MACHINE_eXTRA_RDEPENDS_append_vf = " \
    usb-suspend-resume \
"
