#!/usr/bin/env bash
set -euo pipefail

BOOTSTRAP="${KAFKA_BOOTSTRAP:-localhost:9092}"
PARTITIONS="${KAFKA_PARTITIONS:-3}"
REPLICATION="${KAFKA_REPLICATION:-1}"

echo "Creating Kafka topics on ${BOOTSTRAP}..."

docker exec perf-kafka kafka-topics \
  --bootstrap-server kafka:29092 \
  --create \
  --if-not-exists \
  --topic message-in \
  --partitions "${PARTITIONS}" \
  --replication-factor "${REPLICATION}"

docker exec perf-kafka kafka-topics \
  --bootstrap-server kafka:29092 \
  --create \
  --if-not-exists \
  --topic message-out \
  --partitions "${PARTITIONS}" \
  --replication-factor "${REPLICATION}"

echo "Topics created:"
docker exec perf-kafka kafka-topics --bootstrap-server kafka:29092 --list
