#!/bin/bash

echo "Get submodules.";
git submodule init;
git submodule update;

chmod -v +x ChatScript/BINARIES/LinuxChatScript64;

echo "Building jar."
mvn clean test package;


echo "";
echo "To test, run './ChatScript/BINARIES/LinuxChatScript64 port=2828' in a terminal.";
echo "Then run 'java -jar chatscript-client-0.0.1-jar-with-dependencies.jar'."
