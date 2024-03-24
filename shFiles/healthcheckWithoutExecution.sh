#!/usr/bin/env bash
# Environment Variables
# HUB_HOST
# BROWSER
# MODULE

echo "Checking if hub is ready - localhost"

while [ "$( curl -s http://localhost:4444/wd/hub/status | jq -r .value.ready )" != "true" ]
do
	sleep 1
done
exit 0
