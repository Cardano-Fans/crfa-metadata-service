## Introduction
Service which serves metadata from https://github.com/Cardano-Fans/crfa-offchain-data-registry

## Building
mvn clean package

## Starting
java -jar crfa-metadata-service-0.2.jar

## Requirements
- JDK 11

## Usage
```
curl http://localhost:8080/metadata/by-hash/e45605e3f7d131723422c67353a3d2e0cccc06192e2e92efab9c8deb | jq "."
```
