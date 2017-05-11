# Class to include some extra packages from Host Mobility layers

MACHINE_EXTRA_RDEPENDS_append = " \
    kernel-module-pic \
    u-boot-hostmobility-fw-utils \
    ${@bb.utils.contains('LICENSE_FLAGS_WHITELIST', 'closed', \
                          'packagegroup-hostmobility-commercial', '', d)} \
"

MACHINE_EXTRA_RDEPENDS_append_tegra3 = " \
    e2fsprogs-resize2fs \
"
