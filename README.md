# chatscript-client

This project is a simple POC (Proof Of Concept) of a ChatScript client written in Java.

## ChatScript

ChatScript repository: https://github.com/bwilcox-1234/ChatScript

Note: The Chatscript repo also appears as a git submodule for this project.

## Client

The project is mavenized, so build it with `mvn clean package`.

### Usage

`java -jar chatscript-client-0.0.1-jar-with-dependencies.jar`

```
usage: ChatScriptClient [-b <botname>] [-h] [-n <name>] [-p <port>] [-u <url>]
 -b,--bot <botname>   Botname
 -h,--help            Display help usage
 -n,--name <name>     Username
 -p,--port <port>     Server port
 -u,--url <url>       Server url
```

Note: A ChatScript server must be running in order to test the client.
