#!/usr/bin/env bash

for i in {1..10}
do 
  (curl -X POST localhost:5000/supermarche/clients -d '{"nb": 2}' -H 'Accept: application/json' -H 'Content-type: application/json' ; echo) &
done
