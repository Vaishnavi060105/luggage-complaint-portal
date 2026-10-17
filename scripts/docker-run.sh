#!/usr/bin/env bash
# Build the WAR, build the image and run the container on port 8082
set -e
mvn clean package -DskipTests
docker build -t luggage-portal:1.0 .
docker rm -f portal 2>/dev/null || true
docker run -d --name portal -p 8082:8080 luggage-portal:1.0
echo "Open http://localhost:8082"
