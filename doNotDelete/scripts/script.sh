#!/bin/bash

# My first script







echo "parms info 1" $1



#cd $2

cd $1

mvn -pl . clean install



echo "completed"

