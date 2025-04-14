#!/bin/bash

# clean and compile with maven
mvn clean package

# copy compiled jar to /home/server
target="/home/server/rest_server/"
mkdir -vp "$target"
cp -v target/food-rest-service-0.0.1-SNAPSHOT.jar target/Server.jar
mv -v target/Server.jar "$target"

# copy run script
cp -v runserver.sh "$target"