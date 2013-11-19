OohLaLog Grails Plugin
=====================
The Grails OohLaLog plugin adds a log4j appender to forward traffic to the oohlalog api service. This allows its users to aggregate logs across multiple servers, setup alarms, custom counters, and realtime monitor their application.


Log4J Appender
--------------
Modify your Config.groovy file to add an appender to log4j.

*Example:*
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
Appender properties:

- host ["api.oohlalog.com"]
- path ["/api/logging/save.json"]
- port [80]
- secure [false]
- debug [false]
- hostName [null] // how the log source will appear


The above configuration will automatically forward log messages of info level or higher to your oohlalog app. Simply visit oohlalog.com to signup and get your api token.

Grails Artefact Logging and Counting
------------------------------------

The following methods are available in all Domain Classes, Controllers, and Services

```groovy
	void oohlaCount(String counterName, int increment = 1)

	void oohlaLog(String level, String message, String category = "[artifact name]", 
	         Object exceptionOrStringDetails = null, String token = null, Long timestamp = System.currentTimeMillis() )
```
These methods bypass Log4J level threshold checks.

Level should be one of the standard log4J levels (TRACE, DEBUG, INFO, WARN, ALL, ERROR, FATAL)

Token can be used to relate a sequence of logs together (i.e. specific user activity)

*Example:*
```groovy
class myService {
	def myMethod() {
        oohlaLog('DEBUG','interesting!') //always sent to OohLaLog
        oohlaCount('myMethod.called')
	}

	def myOtherMethod() {
        log.debug('toooo interesting!') // not sent to OohLaLog if threshold is INFO (as above)
	}

}
```

To use these methods please configure the OohLaLoh log4j appender as mentioned above or add the following Config.groovy setting:

```groovy
	oohlalog.authToken='XXXX-XXXX-XXXXXXX'// REQUIRED 
	oohlalog.host="api.oohlalog.com"// OPTIONAL 
	oohlalog.path="/api/logging/save.json" // OPTIONAL 
	oohlalog.port=80 // OPTIONAL 
	oohlalog.secure=false // OPTIONAL 
	oohlalog.debug=false // OPTIONAL 
	oohlalog.hostName=null // OPTIONAL how the log source will appear
```

Appender properties:



Web Transaction Counting
------------------------

**Static Property**

You may enable Controller Action counters by adding a static property to your Controllers:

*Example:*
```groovy
	class MyController {
		static oohLaLogCountActions = true

		...

	}
```

All action requests will be counted as "[Controller name].[Action name]"

**Config.groovy**

You may also enable Controller Action counters via Config.groovy configuration:

```groovy
	oohlalog.webtransactions = true // globally enables counting 
	oohlalog.webtransactions.myController = true // enables counting for a specific controller
	oohlalog.webtransactions.myController = 'MyCtrlr' // enables counting for a specific controller with a custom counter namespace
	oohlalog.webtransactions.myController.myAction = true // enables counting for a specific controller action the default counter name
	oohlalog.webtransactions.myController.myAction = 'myAct' // enables counting for a specific controller action with a custom counter namespace
```

To use these configurations please configure the OohLaLoh log4j appender or Config setting as mentioned above.

**Annotations**

You may also enable controler Controller Action counters via Annotation. The Annotation can be used for the Controller class and the Controller Actions:

*Example:*
```groovy
	import com.oohlalog.grails.OohLaLogWebTransaction
	@OohLaLogWebTransaction('mycont')
	class MyController {

	    def test() { 
	    	render text:'test!!!' // counts as "mycont.test"
	    }

		
		@OohLaLogWebTransaction('my.idx')
	    def index() { 
	    	render text:'index!!!' // counts as "my.idx"
	    }
	}
```

To use the taglib please configure the OohLaLoh log4j appender or Config setting as mentioned above.


Web Browser Logging and Counting
--------------------------------

There are three tags available:

- **&lt;olo:lib/&gt;** - adds and configures the JavaScript library
- **&lt;olo:counterImage/&gt;** - adds an image that will increment a counter when loaded by a client. Accepts "*name*" attribute.
- **&lt;olo:logImage/&gt;** - adds an image that will submit a log when loaded by a client. Accepts "*level*", "*category*", and "*message*" attributes.

*Example:*
```html
<html>
<head>
<olo:lib/>
</head>
<body>
<input type="button" value="Count!" onclick="olo.count('mycounter',1);"/>
Counter Image
<olo:counterImage name="myImageCounter"/>
<br/>
Log Image
<olo:logImage level="INFO" category="myCat" message="hello world"/>
</body>
</html>
```


To use the taglib please configure the OohLaLoh log4j appender or Config setting as mentioned above.





