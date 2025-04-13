#!/bin/bash

mvn clean package
target="/home/server/soap_server/"
mkdir -vp "$target"
cp -v bin/SpringSOAP-0.0.1-SNAPSHOT.jar bin/Server.jar
mv -v bin/Server.jar "$target"