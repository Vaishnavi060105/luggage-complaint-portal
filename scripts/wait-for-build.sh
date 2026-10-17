#!/usr/bin/env bash
# Waits until the deployed app reports the expected build number in /actuator/info.
# Usage: scripts/wait-for-build.sh <app-url> <build-number>
URL="$1"; BUILD="$2"
for i in $(seq 1 40); do
  if curl -sf "$URL/actuator/info" | grep -q "\"build\":\"$BUILD\""; then
    echo "App at $URL is running build $BUILD"; exit 0
  fi
  sleep 5
done
echo "Timed out waiting for $URL to run build $BUILD"; exit 1
