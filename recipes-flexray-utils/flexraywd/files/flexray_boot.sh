#!/bin/bash
WORKING_DIR="/opt/hm"

FIRST_TIME_BOOT_FILE=/usr/bin/first_boot_after_update_flexray.txt
NO_FLEXRAY_FILE=/etc/no_flexray_upgrade
PREFIX=mx4-msg-flexray

# start and restart flexraytimesync utility will syncronize the time in the MX4 with the time used in the FlexRay slave node for timestamping frames.
function start_flexraytimesync()
{

	while true; do
		logger -t ${PREFIX} "Start flexraytimesync"
		/usr/bin/flexraytimesync
		logger -t ${PREFIX} "Stopped flexraytimesync exit:$?"
		sleep 1
	done
}
# start and restart flexrayd demon for udp package to vflexray0.
function start_flexrayd()
{

	while true; do
		logger -t ${PREFIX} "Start flexrayd"
		/usr/bin/flexrayd
		logger -t ${PREFIX} "Stopped flexrayd exit:$?"
		sleep 1
	done
}
# abort script remove start programs.
function Stop_script ()
{
	kill -0 "$flexraytimesync_PID"
	if [[ $? == 0 ]]; then
		kill -9 "$flexraytimesync_PID" &
	fi
	kill -0 "$flexrayd_PID"
	if [[ $? == 0 ]]; then
		kill -9 "$flexrayd_PID" &
	fi
}


## Flexray setup
# gpio-63  - nFR-RST
# gpio-102 - FR-MCU-IN (force-update PIN=disabled=0)

if [ ! -f /sys/class/gpio/gpio63/direction ]; then
echo 63 > /sys/class/gpio/export
fi

if [ ! -f /sys/class/gpio/gpio102/direction ]; then
echo 102 > /sys/class/gpio/export
fi


# Also order is important, FR-MCU-IN must be high before reset is
# released. Which it will be with the current sequence. FR-MCU will
# halt in the boot-loader if FR-MCU-IN is HIGH during startup.
echo high > /sys/class/gpio/gpio102/direction
sleep 1
echo high > /sys/class/gpio/gpio63/direction

modprobe vflexray
ip link add dev vflexray0 type vflexray
ip link set vflexray0 up
modprobe pps_gen_gpio


if [ -f $FIRST_TIME_BOOT_FILE ]; then

	if [ ! -f "$NO_FLEXRAY_FILE" ]; then
		echo "Started FR-MCU firmware upgrade!" > /dev/console
		logger -t ${PREFIX} "Start FR-MCU firmware upgrade..."

		echo "Shutdown FR-MCU"
		echo 0 > /sys/class/gpio/gpio63/value
		
		echo "Enable force-update PIN"
		echo 0 > /sys/class/gpio/gpio102/value
		sleep 1

		echo "Power-on FR-MCU"
		echo 1 > /sys/class/gpio/gpio63/value
		sleep 1

		cd $WORKING_DIR/frmcu

		echo ^Ax^M > $WORKING_DIR/exit_commad.txt
		minicom -S mx-flexray.minicom -t vt102 < $WORKING_DIR/exit_commad.txt

		STATUS=$?
		echo "FR-MCU update exit code: $STATUS"

		cd $WORKING_DIR

		# Disable force-update PIN
		echo "Disable force-update PIN"
		echo 1 > /sys/class/gpio/gpio102/value

		if [ $STATUS -ne 0 ];then
			echo "Update Flexray chip firmware failed!" > /dev/console
			logger -t ${PREFIX} "failed!"
		else
			echo "Update Flexray chip firmware ok" > /dev/console
			logger -t ${PREFIX} "ok"
		fi
	fi

	if [ -f "$NO_FLEXRAY_FILE" ]
	then
		logger -t $PREFIX "Removing file $NO_FLEXRAY_FILE"
		rm $NO_FLEXRAY_FILE
	fi

	if [ -f "$FIRST_TIME_BOOT_FILE" ]
	then
		logger -t $PREFIX "Removing file $FIRST_TIME_BOOT_FILE"
		rm $FIRST_TIME_BOOT_FILE
	fi

fi


#Start flexray programs

trap Stop_script SIGINT

start_flexraytimesync &
flexraytimesync_PID=$!

start_flexrayd &
flexrayd_PID=$!

/usr/bin/flexraywd
echo flexraywd:$?




exit 0
