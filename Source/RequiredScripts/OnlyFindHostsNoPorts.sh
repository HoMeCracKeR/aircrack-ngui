nmap -sn -e $1 $2 | grep -v "Host is up." | grep -v "Starting Nmap" | grep -v "Nmap done"
