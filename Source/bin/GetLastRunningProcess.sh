strAllProcesses=`ps -A | grep "$1" | tail -n 1`;
echo $strAllProcesses;
