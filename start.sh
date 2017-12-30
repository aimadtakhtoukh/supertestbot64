#!/usr/bin/env bash
mvn clean package
docker stop testbot
docker rm testbot
docker build -t testbot .
docker run -d --name testbot --restart=always testbot