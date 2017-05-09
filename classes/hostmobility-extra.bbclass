# Class to include some extra packages from Host Mobility layers

MACHINE_EXTRA_RDEPENDS_append = " \
    kernel-module-pic \
    ${@bb.utils.contains('LICENSE_FLAGS_WHITELIST', 'closed', \
                          'packagegroup-hostmobility-commercial', '', d)} \
"
