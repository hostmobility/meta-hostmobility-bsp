# Provides common Tegra SoC settings on MX-4 products

require conf/machine/include/soc-family.inc

EXTRA_IMAGEDEPENDS += "u-boot"

PREFERRED_PROVIDER_virtual/psplash = "psplash-angstrom"
PREFERRED_PROVIDER_psplash-support = "psplash-angstrom"

# Tegra binaries require a specific X-Server binary ABI
PREFERRED_PROVIDER_virtual/xserver_tegra = "xserver-xorg"
PREFERRED_VERSION_xserver-xorg_tegra = "99:1.14%"
XSERVER = "xserver-xorg \
           xf86-input-evdev \
           xf86-input-mouse \
           xf86-video-fbdev \
           xf86-input-keyboard"

PREFERRED_PROVIDER_virtual/kernel = "linux-hostmobility"

KERNEL_IMAGETYPE = "uImage"

# The kernel lives in a seperate FAT or UBI partition, don't deploy it in /boot
RDEPENDS_kernel-base = ""

PREFERRED_PROVIDER_u-boot = "u-boot-hostmobility"
PREFERRED_PROVIDER_virtual/bootloader = "u-boot-hostmobility"
PREFERRED_PROVIDER_u-boot-fw-utils ?= "u-boot-hostmobility-fw-utils"

PREFERRED_PROVIDER_lxde-icon-theme = "lxde-icon-theme"
PREFERRED_PROVIDER_lxsession-lite = "lxsession"
PREFERRED_PROVIDER_virtual/psplash = "psplash-angstrom"
PREFERRED_PROVIDER_psplash-support = "psplash-angstrom"

PREFERRED_PROVIDER_virtual/mesa = "mesa"
PREFERRED_PROVIDER_virtual/egl = "tdx-nv-binaries"
PREFERRED_PROVIDER_virtual/libgles1 = "tdx-nv-binaries"
PREFERRED_PROVIDER_virtual/libgles2 = "tdx-nv-binaries"

# Install NV binaries which contains tegra X-Server drivers when X11 is chosen
MACHINE_EXTRA_RRECOMMENDS_append_tegra = " ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'tdx-nv-binaries', '', d)}"

# used by sysvinit_2
SERIAL_CONSOLE = "115200 ttyS0"

IMAGE_FSTYPES ?= "tar.xz"
