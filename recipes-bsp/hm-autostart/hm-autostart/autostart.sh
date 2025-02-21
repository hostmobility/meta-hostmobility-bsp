#!/bin/bash

WORKING_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
MACHINE=$(cat /etc/platform-system-type)

FIRST_TIME_BOOT_FILE=/etc/first_boot_after_update.txt

# We can now change the state of the host watchdog from boot to start. This will enable heartbeats resetting the watchdog timer, preventing a reset of the system.
start_watchdog() {
	TIMEOUT=45
    START_TIME=$(date +%s)

    if [[ "$MACHINE" == "imx8mp-var-dart-hmx1" || "$MACHINE" == "verdin-am62-hmm" ]]; then
		#Based on the same rpmsg_ctrl0 but for hmm it start this script to early before the module is running with rpmsg handler so to prevent that from happening it wait for the device handler.
        while [ ! -e /dev/rpmsg_ctrl0 ]; do
            echo "Waiting on RPMSG handler..."
            sleep 1
            CURRENT_TIME=$(date +%s)
            if (( CURRENT_TIME - START_TIME >= TIMEOUT )); then
                echo "Error: Timeout waiting for RPMSG handler"
            fi
        done

        echo "0" > /sys/class/host_watchdog/ctl_start
        echo "Host watchdog started! Booting linux complete!"
    fi
}

# Run the function in the background, wait on it before reading usb and before exit the script.
start_watchdog &
HOST_WD_PID=$!

if [[ -f $FIRST_TIME_BOOT_FILE ]];
then

#Set hostname to ${MACHINE}-SERIAL. TODO this could be compatible with MXV(&& "$MACHINE" == "mx5-pt").
if [[ "$MACHINE" == "imx8mp-var-dart-hmx1" || "$MACHINE" == "verdin-am62-hmm" ]]; then
	# Retrieve the serial number from /proc/device-tree/chosen/serial-number
	serial_number=0
	serial_number=$(cat /proc/device-tree/chosen/serial-number)
	if [[ "$MACHINE" == "imx8mp-var-dart-hmx1" ]]; then
		hostname="HMX-${serial_number}"
	else
		hostname="${MACHINE}-${serial_number}"
	fi
	# Update the hostname
	echo "$hostname" > /etc/hostname
	hostname "$hostname"
fi

# Remove /boot/*.scr from boot so next reboot will not start a new upgrade, not used on hmm instead it would remove the boot-up.scr file if we doth use the bootfs partition.
if [[ "$MACHINE" == "imx8mp-var-dart-hmx1" ]]; then
	rm /boot/*.scr
fi

# Wait for the background process to finish
wait $HOST_WD_PID

#start find and execute autoboot.sh
findautoboottries=0
	while ! test -n "$IFRM_INSTALL_PATH"
	do
		sleep 1
		((findautoboottries+=1))
		echo "Attempting to find autoboot.sh on USB stick. Try $findautoboottries/10"
		if test $findautoboottries -eq 10
		then
			break
		fi
		IFRM_INSTALL_PATH=$(find /media -name autoboot.sh)
		if [[ -z "$IFRM_INSTALL_PATH" ]]; then
		  IFRM_INSTALL_PATH=$(find /run/media -name autoboot.sh)
		fi
	done

	if [ -n "$IFRM_INSTALL_PATH" ]; then
		touch /etc/autoboot_started.txt
		sh $IFRM_INSTALL_PATH
	fi
	rm $FIRST_TIME_BOOT_FILE
fi

wait $HOST_WD_PID

exit 0
