#!/bin/bash

ant init
ant compile
target="/home/server/rmi_server/"
mkdir -vp "$target"
cp -vR bin/hotel/ "$target"
printf "Manifest-Version: 1.0\nMain-Class: staff.BookingServer\n" >> bin/MANIFEST.MF
(cd bin && jar cfm Server.jar MANIFEST.MF .)
cp -v bin/Server.jar "$target"