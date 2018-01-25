#!/bin/bash

echo "Getting submodules.";
git submodule init;
git submodule update;
echo "";

echo "Add executions right to the ChatScript server.";
chmod -v +x ChatScript/BINARIES/LinuxChatScript64;
echo "";

echo "Building jar."
mvn clean test package;
echo "";

echo "To test, run './ChatScript/BINARIES/LinuxChatScript64 port=2828' in a terminal.";
echo "Then run 'java -jar ./target/chatscript-client-0.0.1-jar-with-dependencies.jar'."
echo "";

java -jar ./target/chatscript-client-0.0.1-jar-with-dependencies.jar -h
