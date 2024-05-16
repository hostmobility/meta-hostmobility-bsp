#!/bin/bash

WORKING_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
MACHINE=$(cat /etc/platform-system-type)

FIRST_TIME_BOOT_FILE=/etc/first_boot_after_update.txt

# we can now set the host watchdog to start state from boot(this will start the heartbeats to prevent reset of the system)
if [[ "$MACHINE" == "imx8mp-var-dart-hmx1" ]]; then
	echo "0" > /sys/class/host_watchdog/ctl_start
fi

if [[ -f $FIRST_TIME_BOOT_FILE ]];
then

#Set hostname to ${MACHINE}-SERIAL. TODO this could be compatible with MXV(&& "$MACHINE" == "mx5-pt").
if [[ "$MACHINE" == "imx8mp-var-dart-hmx1" ]]; then
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

# Remove /boot/*.scr from boot so next reboot will not start a new upgrade.
rm /boot/*.scr

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

exit 0