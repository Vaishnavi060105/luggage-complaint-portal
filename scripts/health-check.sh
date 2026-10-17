#!/usr/bin/env bash
# Usage: scripts/health-check.sh [base-url]
BASE_URL="${1:-http://localhost:8081}"
curl -sf "$BASE_URL/actuator/health" && echo || { echo "Health check FAILED"; exit 1; }
