#!/bin/bash
# check-config-server-started.sh

apt-get update -y

yes | apt-get install curl

curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://kafka-schema-registry:8081/)

echo "result status code:" "$curlResult"

while [[ ! $curlResult == "200" ]]; do
  echo "result status code:" "$curlResult"
  >&2 echo "Schema Registry server is not up yet!"
  sleep 2
  curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://kafka-schema-registry:8081/)
done

exec /cnb/process/web
