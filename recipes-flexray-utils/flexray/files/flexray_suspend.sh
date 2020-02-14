#!/bin/bash

## Flexray setup (sleep/suspend)
# gpio-63  - nFR-RST
# gpio-102 - FR-MCU-IN (force-update PIN=disabled=0)

echo low > /sys/class/gpio/gpio63/direction
echo low > /sys/class/gpio/gpio102/direction

exit 0
