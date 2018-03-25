#!/bin/bash
cp test test.temp
while read line
do
    tget $line
    if [ $? -eq 0 ]; then
        sed "1d" test
    fi
done < test.temp
