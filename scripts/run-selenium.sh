#!/usr/bin/env bash
# Usage: scripts/run-selenium.sh [base-url]   (default http://localhost:8081)
BASE_URL="${1:-http://localhost:8081}"
mvn test -Pselenium -Dbase.url="$BASE_URL"
