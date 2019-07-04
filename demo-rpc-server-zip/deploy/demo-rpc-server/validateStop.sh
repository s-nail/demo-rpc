#!/bin/bash
cd demo-rpc-server
pidFile="demo-rpc-server.pid"
if [ -f "$pidFile" ]
then
	PID=$(cat demo-rpc-server.pid)
	i=10
	while [[ $i -gt 0 ]];do
		sleep 1
		check_start=`ps axu | grep java | grep $PID |grep -v grep| awk '{printf $2}'`
		if [ -z "$check_start" ]
		then
			break
		fi
		((i = i - 1))
	done
	if [[ $i -gt 0 ]]
	then
		rm -f demo-rpc-server.pid
	    echo 0
	else
		echo echo "No stopping!" 1>&2
	fi
else
	echo 0
fi

