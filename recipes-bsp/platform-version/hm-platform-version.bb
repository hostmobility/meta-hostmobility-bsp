SUMMARY = "Platform version information"
DESCRIPTION = "Save build versions as a part of Host Mobility/SETEK Board Support Package"
LICENSE = "CLOSED"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/${sysconfdir}

    echo ${MACHINE} > ${D}/${sysconfdir}/platform-system-type

    echo ${BUILD_TAG} > ${D}/${sysconfdir}/platform-build-tag 
    echo ${DISTRO_VERSION} > ${D}/${sysconfdir}/platform-bsp-version
    echo ${PLATFORM_VERSION} > ${D}/${sysconfdir}/platform-version
    echo "${PLATFORM_VERSION_DETAILS}" > ${D}/${sysconfdir}/platform-version-details
    echo "${DISTRO_CODENAME}" > ${D}/${sysconfdir}/platform-branch-name
}


FILES:${PN} = "\
    ${sysconfdir}/platform-system-type \
    ${sysconfdir}/platform-board-type \
    ${sysconfdir}/platform-version \
    ${sysconfdir}/platform-branch-name \
    ${sysconfdir}/platform-bsp-version \
    ${sysconfdir}/platform-version-details \
    ${sysconfdir}/platform-build-tag \
"
