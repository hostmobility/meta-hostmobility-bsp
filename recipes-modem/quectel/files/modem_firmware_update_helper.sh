#!/bin/bash

system_type=$(cat /etc/platform-system-type)

start_recovery_state()
{
	
	echo 1 > /opt/hm/pic_attributes/ctrl_modem_emg
	sleep 60
	
	# Check if /dev/ttyUSB0 exists
	if [ -c /dev/ttyUSB0 ]; then
		echo "Modem is in recovery state from start. Start flashing!"
		# Try using the direct command if the modem is already in recovery mode
		/usr/local/bin/QFirehose -f $1
		exit $?
	else
		echo "Could not find modems. Try powering off the unit and try again."
		exit 1
	fi
}

start_recovery_state_v2()
{
	
	# Check if /dev/ttyUSB0 exists
	if [ -c /dev/ttyUSB0 ]; then
		echo "Modem is in recovery state from start. Start flashing!"
		# Try using the direct command if the modem is already in recovery mode
		/usr/local/bin/QFirehose -f $1
		exit $?
	else
		echo "Could not find modems. Try powering off the unit and try again."
		exit 1
	fi
}

update_modem()
{
# Check if /dev/ttyUSB2 exists
if [ ! -c /dev/ttyUSB2 ]; then
    # Run the QFirehose command
    .//usr/local/bin/QFirehose -f $1 -p /dev/ttyUSB2
    if [ $? -ne 0 ]; then
        echo "Failed with no ttyUSB2"
		return 1
    else
        exit 0
    fi
fi
}


modem_wait()
{
	# Check modem status in loop with timeout
	timeout=60
	echo "Modem startup wait $timeout s"
	start_time=$(date +%s)
	end_time=$((start_time + timeout))
	
	while true; do
		current_time=$(date +%s)
		echo "Waiting for modem: $current_time until $end_time"
		if [[ $current_time -gt $end_time ]]; then
			echo "Timeout waiting for modem startup, try enter modem recovery state update"
			return 1
		fi
		
		MODEM_STATE=$(/opt/hm/modem_status.sh | grep "$modem_state_line" | awk '{print $3}')
			if [[ "$MODEM_STATE" == "ON" ]]; then
			echo "modem started"
			return 0
			fi
	
		sleep 1
	done

}

#################main#################
#Does startup the modem direct
if [[ "$system_type" == "imx8mp-var-dart-hmx1" ]]; then
	update_modem $1 || start_recovery_state_v2 $1
fi
#mxv way
if [[ "$system_type" == "mx5-pt" ]]; then
	if gpioset $(gpiofind MODEM_ENABLE_ON)=0; then
		echo "FAILED gpioset (gpiofind MODEM_ENABLE_ON) off!"
	fi
	echo "Modem off for 30s"
	sleep 30
	if gpioset $(gpiofind MODEM_ENABLE_ON)=1; then
		echo "FAILED gpioset (gpiofind MODEM_ENABLE_ON) on!"
	fi
	echo "modem waitng to startup for 60s"
	sleep 60
	echo "modem started"
	update_modem $1 || start_recovery_state_v2 $1
fi
#mx4 way
if [[ "$system_type" == "mx4-ct" || "$system_type" == "mx4-c61"* ]]; then

	if [[ ! -d $1 ]]; then
		echo "Add a folder for modem firmware, example EG25GGBR07A08M2G_30.006.30.006."
		exit 1
	fi

	echo -en '0' > /opt/hm/pic_attributes/ctrl_modem_on
	echo "Modem off for 30s"
	sleep 30
	echo -en '43690' > /opt/hm/pic_attributes/ctrl_modem_on
	modem_wait || start_recovery_state $1

	update_modem $1 || start_recovery_state $1
fi
