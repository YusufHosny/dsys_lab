#!/bin/bash

mvn clean package
target="/home/server/rest_server/"
mkdir -vp "$target"
cp -v target/SpringSOAP-0.0.1-SNAPSHOT.jar target/Server.jar
mv -v target/Server.jar "$target"