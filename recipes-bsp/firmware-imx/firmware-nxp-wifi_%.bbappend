# Workaround for NXP bug: meta-imx bbappend duplicates ${PN}-nxpiw610-sdio
# which was added to base recipe in meta-freescale commit 39b41e6 (Jan 2025)
# TODO: Remove this workaround when NXP fixes their bbappend

# Remove duplicate package names before populate_packages runs.
python populate_packages:prepend() {
    packages = d.getVar('PACKAGES') # Returns String or None
    if packages:
        unique_packages = list(dict.fromkeys(packages.split()))
        d.setVar('PACKAGES', ' '.join(unique_packages))
}
