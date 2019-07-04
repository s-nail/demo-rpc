#!/bin/sh
cd demo-rpc-server
pidFile="demo-rpc-server.pid"
if [ -f "$pidFile" ]
then
	PID=$(cat demo-rpc-server.pid) 
	if [ -n "$PID" ]
	then
	    kill $PID
	fi
else
	echo 0
fi