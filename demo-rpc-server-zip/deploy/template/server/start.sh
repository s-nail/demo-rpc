#!/bin/sh
cd demo-rpc-server
nohup java ${jvm_args} ${log4j2_args} -jar -DAppPID demo-rpc-server.jar >/dev/null  &