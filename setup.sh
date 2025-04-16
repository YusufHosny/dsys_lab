#!/bin/bash

rm -rf /home/server

(cd lab1 && bash setup.sh)
(cd lab2_soap && bash setup.sh)
(cd lab2_rest && bash setup.sh)

cp -v runserver.sh /home/server/
