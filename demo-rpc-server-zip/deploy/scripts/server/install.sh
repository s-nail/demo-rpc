##---------备份运行目录----------##
now=`date "+%Y%m%d%H%M%S"`
finalname="demo-rpc-server-"$now
if [ ! -d ./backup ];then
mkdir ./backup &>/dev/null
fi
if [ -d ./demo-rpc-server ];then
cp -R ./demo-rpc-server ./backup/$finalname &>/dev/null
fi
#!/bin/bash
firewallState=$(firewall-cmd --state 2>&1)
if [ "$firewallState" == "running" ] ; then
	ports=$(firewall-cmd --zone=public --list-ports)
	echo "opened ports:$ports"
	if [[ ! "$ports" =~ ${app_server_port} ]] ; then
	  echo "open tcp port ${app_server_port}"
	  firewall-cmd --zone=public --add-port=${app_server_port}/tcp --permanent
	fi
	if [[ ! "$ports" =~ ${rpc_protocol_port} ]] ; then
	  echo "open tcp port ${rpc_protocol_port}"
	  firewall-cmd --zone=public --add-port=${rpc_protocol_port}/tcp --permanent
	fi
	systemctl restart firewalld.service
fi
rm -rf demo-rpc-server
cp -a tmp/demo-rpc-server/demo-rpc-server ./
chmod -R 755 ./demo-rpc-server