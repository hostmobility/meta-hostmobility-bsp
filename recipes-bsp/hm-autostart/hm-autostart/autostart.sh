#!/bin/bash

# Early boot helper:
#  - enable watchdog monitoring
#  - provision hostname from EEPROM
#  - run USB autoboot.sh on first boot after update

MACHINE="$(cat /etc/platform-system-type)"

FIRST_TIME_BOOT_FILE="/etc/first_boot_after_update.txt"
HOSTNAME_PENDING_FILE="/etc/hostname_from_eeprom_pending.txt"
HOSTNAME_WAIT_SECONDS="${HOSTNAME_WAIT_SECONDS:-5}"

get_serial_number() {
    [[ -r /proc/device-tree/chosen/serial-number ]] || return 1
    tr -d '\0' < /proc/device-tree/chosen/serial-number
}

is_valid_serial_number() {
    local s="$1"
    [[ -n "$s" ]] || return 1
    [[ "$s" =~ [Xx][Xx][Xx] ]] && return 1
    [[ "$s" =~ ^0+$ ]] && return 1
    return 0
}

# Set hostname to MACHINE-SERIAL (or HMX-SERIAL)
set_hostname_if_needed() {
  local newhost current i serial=""
  [[ "$MACHINE" == "imx8mp-var-dart-hmx1" || "$MACHINE" == "verdin-am62-hmm" ]] || return 0
  [[ -f "$HOSTNAME_PENDING_FILE" ]] || return 0

  echo "Hostname provisioning pending; waiting up to ${HOSTNAME_WAIT_SECONDS}s for a valid serial number..."

  for ((i=1; i<=HOSTNAME_WAIT_SECONDS; i++)); do
    serial="$(get_serial_number 2>/dev/null)" || serial=""
    if is_valid_serial_number "$serial"; then
      break
    fi
    sleep 1
  done

  if is_valid_serial_number "$serial"; then
    if [[ "$MACHINE" == "imx8mp-var-dart-hmx1" ]]; then
      newhost="HMX-${serial}"
    else
      newhost="${MACHINE}-${serial}"
    fi

    current="$(cat /etc/hostname 2>/dev/null || true)"
    if [[ "$current" != "$newhost" ]]; then
      echo "$newhost" > /etc/hostname
      hostname "$newhost"
    fi

    echo "Hostname set to $newhost (serial '$serial'); removing '$HOSTNAME_PENDING_FILE'."
    rm -f "$HOSTNAME_PENDING_FILE"
  else
    echo "Serial still invalid ('$serial'); keeping $HOSTNAME_PENDING_FILE for next boot."
  fi
}

# Set host watchdog to monitoring mode.
start_watchdog() {
  local timeout=45
  local start_time current_time

  start_time="$(date +%s)"

  if [[ "$MACHINE" == "imx8mp-var-dart-hmx1" || "$MACHINE" == "verdin-am62-hmm" ]]; then
    # On hmm this script can start too early; wait for rpmsg handler.
    while [[ ! -e /dev/rpmsg_ctrl0 ]]; do
      echo "Waiting on RPMSG handler..."
      sleep 1
      current_time="$(date +%s)"
      if (( current_time - start_time >= timeout )); then
        echo "Error: Timeout waiting for RPMSG handler"
      fi
    done

    echo "0" > /sys/class/host_watchdog/ctl_start
    echo "Host watchdog started (monitoring enabled)."
  fi
}

find_usb_autoboot() {
  local file="autoboot.sh" path="" try_limit=10 i dir
  local search_paths=(/media /run/media)

  for ((i=1; i<=try_limit; i++)); do
    echo "Searching for $file on USB stick ($i/$try_limit)"
    sleep 1

    for dir in "${search_paths[@]}"; do
      [[ -d "$dir" ]] || continue
      path="$(find "$dir" -name "$file" 2>/dev/null | head -n 1)"
      [[ -n "$path" ]] && break
    done
    [[ -n "$path" ]] && break
  done

  [[ -n "$path" ]] || return 1
  echo "$path"
}

# Run watchdog in background, wait on it before reading usb.
start_watchdog &
HOST_WD_PID=$!

# Run hostname provisioning in background (decoupled from update first-boot).
set_hostname_if_needed &
HOSTNAME_PID=$!

waited_for_wd=0

if [[ -f "$FIRST_TIME_BOOT_FILE" ]]; then
  if [[ "$MACHINE" == "imx8mp-var-dart-hmx1" ]]; then
    # Remove /boot/*.scr from so next reboot will not start a new
    # upgrade. Not used on hmm.
    rm -f /boot/*.scr
  fi

  # Ensure watchdog has transitioned to "start" (monitoring mode)
  # before running any USB autoboot.sh
  wait "$HOST_WD_PID"
  waited_for_wd=1

  if USB_AUTOBOOT_SCRIPT_PATH="$(find_usb_autoboot)"; then
    touch /etc/autoboot_started.txt
    sh "$USB_AUTOBOOT_SCRIPT_PATH"
  fi

  rm -f "$FIRST_TIME_BOOT_FILE"
fi

if (( ! waited_for_wd )); then
  wait "$HOST_WD_PID"
fi

wait "$HOSTNAME_PID" || true

exit 0
