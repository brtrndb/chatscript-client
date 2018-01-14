# chatscript-client

This project is a simple POC (Proof Of Concept) of a ChatScript client written in Java.

## ChatScript

ChatScript repository: https://github.com/bwilcox-1234/ChatScript

Note: The Chatscript repo also appears as a git submodule for this project.

## Client

The project is mavenized, so build it with `mvn clean package`.

### Usage

`java -jar chatscript-client-0.0.1-jar-with-dependencies.jar <url> <port> <username> <botname>`

List of parameters:
- url: ChatScript server address (required).
- port: ChatScript server port (required, ChatScript default port 1024).
- username: Client username (optional, default MacClane).
- botname: Server botname (optional, default harry).
