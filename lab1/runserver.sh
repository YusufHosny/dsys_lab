#!/bin/bash

pkill rmiregistry
sleep 1
(cd /home/server/rmi_server/ && rmiregistry) &
sleep 1
(cd /home/server/rmi_server/ && java -Djava.rmi.server.hostname=$1 -jar Server.jar)