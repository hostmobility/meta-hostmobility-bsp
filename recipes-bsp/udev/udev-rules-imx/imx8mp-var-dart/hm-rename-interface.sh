#!/bin/bash
#
# SPDX-License-Identifier: CC0-1.0
#
#==============================================================================
# Network Interface Rename Script
#==============================================================================
#
# DESCRIPTION:
#   This script provides reliable interface renaming as part of a udev-triggered
#   system for creating stable network interface names. It works in conjunction
#   with two udev rules to ensure consistent interface naming across reboots.
#
# PURPOSE:
#   Renames temporary interface names (like tmp_eth0, tmp_can1) to final stable
#   names (like eth0, can1) after hardware initialization is complete but before
#   network configuration begins.
#
# INTEGRATION:
#   - Triggered by udev rule: 99-hm-interface-rename.rules
#   - Works after: 10-hm-temp-names.rules creates temporary names
#   - Runs before: systemd-networkd applies network configuration
#
# CONCURRENCY:
#   Uses file locking (/run/interface-rename.lock) to serialize interface
#   renames, preventing systemd-networkd from seeing simultaneous interface
#   name changes that could cause processing conflicts.
#
# DEPENDENCIES:
#   - bash (for script execution)
#   - iproute2 (for 'ip' command)  
#   - systemd (for systemd-cat logging)
#   - util-linux (for 'flock' command)
#
# USAGE:
#   Called automatically by udev rule with interface name as argument
#   Example: /opt/hm/hm-rename-interface.sh tmp_eth0
#
#==============================================================================

INTERFACE="$1"
FINAL_NAME="${INTERFACE#tmp_}"

log_message() {
    echo "hm-rename: $1" | systemd-cat -t "hm-rename" -p info
}

interface_is_ready() {
    local iface="$1"
    # Must have operstate file (indicates kernel driver initialized)
    [[ -r "/sys/class/net/$iface/operstate" ]] || return 1
    return 0
}

# Serialize all interface renames using file locking
LOCK_FILE="/run/interface-rename.lock"
(
    flock -x 200
    
    attempt=1
    max_attempts=20  # 2 seconds max
    
    while [[ $attempt -lt $max_attempts ]]; do
        if interface_is_ready "$INTERFACE"; then
            if ip link set dev "$INTERFACE" name "$FINAL_NAME" 2>/dev/null; then
                log_message "Renamed $INTERFACE to $FINAL_NAME (${attempt} attempts)"
                exit 0
            fi
        fi
        sleep 0.1
        ((attempt++))
    done
    
    log_message "Failed to rename $INTERFACE after ${max_attempts} attempts"
    exit 1
    
) 200>"$LOCK_FILE"