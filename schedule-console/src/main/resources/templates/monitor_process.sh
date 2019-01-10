#!/bin/bash

# 作者:杨华
# 创建时间:2007-09-06
# 脚本目的:监控java进程状态
# 修改原因:[修改请注明原因]
# 修改时间:[修改请注明时间]
# 修改作者:[修改请注明作者]

VARS=$#
if [ $VARS -lt 2 ];
then
        echo "必须传入2个参数,第一个参数是进程名称,第二个参数是进程参数"
        exit 0;
fi


PROCESS_NAME=$1
PROCESS_PARM=$2
PROCESS_COUNT=0

PROCESS_COUNT=`ps -ef|grep $PROCESS_NAME | grep $PROCESS_PARM | grep java | grep -v grep | wc -l`

if [ $PROCESS_COUNT -gt 0 ];
then
        echo "PROCESS_EXIST"
else
	echo "$1 $2 PROCESS_NOT_EXIST"
fi
