#!/bin/sh

# 目的:设置通用环境变量

# 警告:请不要随意修改
# 作者:杨华
# 邮件:yanghua@asiainfo.com
# 创建时间:2007-04-25
# 脚本目的:启动独立java进程
# 修改原因:[修改请注明原因]
# 修改时间:[修改请注明时间]
# 修改作者:[修改请注明作者]

# *************************************************************************
#JAVA_OPTIONS - java启动选项
# JAVA_VM      - jvm选项
# MEM_ARGS     - 内存参数
# *************************************************************************
#echo "*************************************************"

#echo "初始化通用环境参数"

JAVA_HOME="/home/schedule/support/jdk1.6.0_45"
echo "JAVA_HOME=${JAVA_HOME}"


JAVA_OPTIONS="  -XX:MaxPermSize=256m -Doracle.jdbc.V8Compatible=true -Dweblogic.ThreadPoolSize=50 -Dweblogic.ThreadPoolPercentSocketReaders=80 -Djava.net.preferIPv4Stack=true -Dsun.net.inetaddr.ttl=10 "

MEM_ARGS="-Xms128m -Xmx128m"

#echo "初始化通用环境参数完成"

#echo $CLASSPATH

#echo "*************************************************"
#echo "\n"