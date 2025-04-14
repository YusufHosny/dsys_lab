#!/bin/bash

curl -v localhost:8080/rest/order -H 'Content-type:application/json' \
  --data '{
    "address": "Brazil",
    "orderItems": {
      "4237681a-441f-47fc-a747-8e0169bacea1": 2,
      "cfd1601f-29a0-485d-8d21-7607ec0340c8": 3
      }
    }'
