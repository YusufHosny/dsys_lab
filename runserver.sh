#!/bin/bash

pkill java
pkill bash

(cd rmi_server && bash runserver.sh "$1") &
(cd soap_server && bash runserver.sh) & 
(cd rest_server && bash runserver.sh) &


