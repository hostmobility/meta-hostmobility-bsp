FILESEXTRAPATHS:prepend := "${THISDIR}/alsa-state/:"

#make this machine specific, as we have different codecs with different settings
PACKAGE_ARCH = "${MACHINE_ARCH}"

#Change default with for example alsamixer and then use alasctl store and find the file asound.state on your machine.