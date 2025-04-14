#!/bin/bash

# ant to compile classes
ant init
ant compile

# package client jar and move to /assignment/rmi
printf "Manifest-Version: 1.0\nMain-Class: staff.BookingClient\n" >> bin/MANIFEST.MF
(cd bin && jar cfm Client.jar MANIFEST.MF hotel/ staff/)
rm "../assignment/service_tester/rmi/Client.jar"
cp -v bin/Client.jar "../assignment/service_tester/rmi/"

# copy run client script to assignment/rmi
cp -v runclient.sh "../assignment/service_tester/rmi/"

# package server jar and move to /home/server
target="/home/server/rmi_server/"
mkdir -vp "$target"

cp -vR bin/hotel/ "$target"

rm bin/MANIFEST.MF
printf "Manifest-Version: 1.0\nMain-Class: staff.BookingServer\n" >> bin/MANIFEST.MF
(cd bin && jar cfm Server.jar MANIFEST.MF hotel/ staff/)
cp -v bin/Server.jar "$target"

# copy run script
cp -v runserver.sh "$target"