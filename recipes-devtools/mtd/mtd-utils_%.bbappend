
# We override Toradex .bbappend which enables -m32 and this breaks build on x64 machines
EXTRA_OEMAKE:class-native = "'CC=${CC}' 'RANLIB=${RANLIB}' 'AR=${AR}' 'CFLAGS=${CFLAGS} -I${S}/include -DWITHOUT_XATTR' 'BUILDDIR=${S}'"
