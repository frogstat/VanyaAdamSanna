#!/bin/bash
if [ -e target/VAS_Maven-1.0-SNAPSHOT.jar ]; then
	java -jar target/VAS_Maven-1.0-SNAPSHOT.jar
else
	echo "There is no game file to run. Have you packaged it?"
fi
