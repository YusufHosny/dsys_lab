#!/bin/bash

mvn clean package
target="/home/server/rest_server/"
mkdir -vp "$target"
cp -v bin/SpringSOAP-0.0.1-SNAPSHOT.jar bin/Server.jar
mv -v bin/Server.jar "$target"