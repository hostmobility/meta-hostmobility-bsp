#!/bin/bash

SOC_BASE="/sys/devices/platform/soc@0"
declare -A needs_renaming

prepare_rename()
{
    if [[ $# != 2 ]]; then
        echo "Expecting /sys path and correct name"
        return 2
    fi
    syspath="$1"

    (( t_timeout=SECONDS+7 ))
    while [[ ! -r "$syspath" ]]; do
        if ((SECONDS>t_timeout)); then
            echo "$syspath does not exist"
            return 3
        fi
        sleep 0.2
    done

    current_name=$(basename "$1")
    right_name="$2"
    if [[ "$current_name" != "$right_name" ]]; then
        needs_renaming[$syspath]=$right_name
        read -r original_state <"$syspath/operstate"
        if [[ $original_state == up ]]; then
            bring_back_up+=("$syspath")
        fi
    fi
}


prepare_rename $SOC_BASE/30800000.bus/3084*.spi/spi_master/spi*/spi*.0/net/* can0
prepare_rename $SOC_BASE/30800000.bus/308d0000.can/net/*                     can1
prepare_rename $SOC_BASE/30800000.bus/308c0000.can/net/*                     can2
prepare_rename $SOC_BASE/30800000.bus/3084*.spi/spi_master/spi*/spi*.1/net/* can3
prepare_rename $SOC_BASE/30800000.bus/3082*.spi/spi_master/spi*/spi*.2/net/* can4
prepare_rename $SOC_BASE/30800000.bus/3082*.spi/spi_master/spi*/spi*.3/net/* can5

# First T1
prepare_rename $SOC_BASE/30800000.bus/30be0000.ethernet/net/* eth2
# Second T1
prepare_rename $SOC_BASE/30800000.bus/30bf0000.ethernet/net/* eth3
# RJ45
prepare_rename $SOC_BASE/32f10108.usb/38200000.usb/xhci-hcd.1.auto/usb1/1-1/1-1.1/1-1.1:1.0/net/* eth1
# Ethernet in Vehicle connector
prepare_rename $SOC_BASE/32f10108.usb/38200000.usb/xhci-hcd.1.auto/usb1/1-1/1-1.2/1-1.2.1/1-1.2.1:1.0/net/* eth0

if (( ${#needs_renaming[@]} > 0 )); then
    echo "--- Renaming needed ---"
fi

for syspath in "${!needs_renaming[@]}"; do
    current_path=$(dir $(dirname "$syspath"))
    current_name=$(basename "$current_path")
    #echo "Current name is $current_name" 
    right_name=${needs_renaming[$syspath]}
    if [[ -d /sys/class/net/$right_name ]]; then
        #echo "$right_name exists"
        # Move current occupant
        echo "$right_name => was_$right_name"
        ip link set "$right_name" down
        ip link set dev "$right_name" name "was_$right_name"
    fi
    echo "$current_name => $right_name"
    ip link set "$current_name" down
    ip link set dev "$current_name" name "$right_name"
done

for syspath in "${bring_back_up[@]}"; do
    current_path=$(dir $(dirname "$syspath"))
    current_name=$(basename "$current_path")
    echo "$current_name" UP
    ip link set "$current_name" up
done

