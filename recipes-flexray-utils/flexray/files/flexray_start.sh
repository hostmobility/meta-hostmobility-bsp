#!/bin/bash
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



exit 0