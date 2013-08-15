OohLaLog Grails Plugin
=====================
The Grails OohLaLog plugin adds a log4j appender to forward traffic to the oohlalog api service. This allows its users to aggregate logs across multiple servers, setup alarms, custom counters, and realtime monitor their application.


Usage
-----
Simply modify your Config.groovy file to add an appender to log4j.

```groovy
log4j = {
  appenders {
    appender new com.oohlalog.log4j.OohLaLogAppender( name: "oohlalog", authToken: "my-api-token", host: "api.oohlalog.com")
  }
  root {
  	info 'oohlalog'
  }
}
```
The above configuration will automatically forward log messages of info level or higher to your oohlalog app. Simply visit oohlalog.com to signup and get your api token.
