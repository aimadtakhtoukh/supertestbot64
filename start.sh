#!/usr/bin/env bash
mvn clean package
docker stop testbot
docker rm testbot
docker build -t testbot .
docker run -d --name testbot --env-file ./env.list --restart=always testbot