MACHINE_EXTRA_RDEPENDS_append = " \
    kernel-modules \
    u-boot-hostmobility-fw-utils \
    ${@bb.utils.contains('LICENSE_FLAGS_WHITELIST', 'closed', \
                          'packagegroup-hostmobility-commercial', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'wifi', \
                          'linux-firmware-ath9k linux-firmware-wl18xx hostapd', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'flash', \
                          'mtd-utils mtd-utils-ubifs mtd-utils-misc mtd-utils-jffs2', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'alsa', \
                          'alsa-utils', '', d)} \
"

MACHINE_EXTRA_RDEPENDS_append_tegra3 = " \
    e2fsprogs-resize2fs \
    fs-init \
    packagegroup-hostmobility-flexray-utils \
"

MACHINE_EXTRA_RDEPENDS_append_vf = " \
    usb-suspend-resume \
"

CORE_IMAGE_EXTRA_INSTALL += " \
    kernel-module-pic \
"