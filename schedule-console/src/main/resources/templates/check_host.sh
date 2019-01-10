#!/bin/bash
function makeDir()
{
    path=$1;
    if [ ! -e $path ] || [ ! -d $path ];
    then
        mkdir -p $path
    fi
}

dirs=$1
OLD_IFS="$IFS"
IFS=","
arr=($dirs)
IFS="$OLD_IFS"

for s in ${arr[@]}
do 
  makeDir $s
done
