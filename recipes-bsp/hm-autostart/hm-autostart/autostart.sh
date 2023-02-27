#!/bin/bash

WORKING_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

#set RGB LED Green
if [[ "$(cat /etc/platform-system-type)" == "imx8mp-var-dart-hmx1" ]]; then
    i2cset -y 1 0x30 8 0x50;
    i2cset -y 1 0x30 4 0x10;
fi

FIRST_TIME_BOOT_FILE=/etc/first_boot_after_update.txt

if [[ -f $FIRST_TIME_BOOT_FILE ]];
then

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