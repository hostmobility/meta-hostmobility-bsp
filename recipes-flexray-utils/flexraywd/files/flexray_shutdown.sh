#!/bin/bash

## Flexray setup (sleep or shutdown)
# gpio-63  - nFR-RST
# gpio-102 - FR-MCU-IN (force-update PIN=disabled=0)

echo low > /sys/class/gpio/gpio63/direction
echo low > /sys/class/gpio/gpio102/direction

echo 63 > /sys/class/gpio/unexport
echo 102 > /sys/class/gpio/unexport

modprobe -r pps_gen_gpio

#remove the virtual interface
#ip link set vflexray0 down
#ip link delete dev vflexray0
#modprobe -r vflexray

exit 0
