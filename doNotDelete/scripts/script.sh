#!/bin/bash
# My first script

echo "started"

echo $1
echo $2

echo "parms info"
cd $2
mvn -pl . clean install

echo "completed"