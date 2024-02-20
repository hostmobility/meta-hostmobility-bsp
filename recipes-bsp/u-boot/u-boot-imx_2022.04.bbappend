FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

MACHINE_PATCHES:mx4-c61 = "\
    file://0003-Add-boot-type-C61.patch \
"
MACHINE_PATCHES:mx4-c61-rio = "\
    file://0003-Add-boot-type-C61-RIO.patch \
"

MACHINE_PATCHES:mx4-v61 = "\
    file://0003-Add-boot-type-V61.patch \
"

SRC_URI += "\
    file://fw_env.config \
    file://0001-MX4-C61-migrated-with-co-cpu-spi-support.patch \
    file://0002-VF60-vc61-Add-hm-special-boot-into-colibri_vf-boot.patch \
    ${MACHINE_PATCHES} \
"



#Keep this version until we can step up u-boot hm patches.
SRCBRANCH = "lf_v2022.04"
SRCREV = "181859317bfafef1da79c59a4498650168ad9df6"

COMPATIBLE_MACHINE = "(vf60)"


nand_padding () {
    # pad the end of U-Boot with 0x00 up to the the end of the CSF area
    #PAD_END=$(echo -n "0x"; od -X  -j 0x24 -N 4 u-boot.imx | sed -e '/................/!d' -e 's/........\(.*\)/\1/')
    #PAD_END=$(( $PAD_END - 0x400 ))
    #objcopy -I binary -O binary --pad-to $PAD_END u-boot.imx u-boot.imx.zero-padded
    # assume that the above never need more than 10k of padding and skip the
    # shell magic to get a correct size.
    dd bs=10k count=1 if=/dev/zero | cat u-boot.imx - > u-boot.imx.zero-padded

    # U-Boot is flashed 1k into a NAND block, create a binary which prepends
    # U-boot with 1k of zeros to ease flashing
    dd bs=1024 count=1 if=/dev/zero | cat - u-boot.imx.zero-padded > u-boot-nand.imx
}

do_compile:append:vf60 () {
    nand_padding
}