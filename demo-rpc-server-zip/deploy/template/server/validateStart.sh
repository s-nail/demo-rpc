#!/bin/bash
cd demo-rpc-server
pidFile="demo-rpc-server.pid" 
i=120
while [[ $i -gt 0 ]];do
	sleep 10
	if [ -f "$pidFile" ]
	then
		break
	fi
	((i = i - 10))
done
if [[ $i -gt 0 ]]
then
    echo 0
else
	echo "start failed" 1>&2
fi


