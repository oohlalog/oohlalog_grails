OohLaLog Grails Plugin
=====================
The Grails OohLaLog plugin adds a log4j appender to forward traffic to the oohlalog api service. This allows its users to aggregate logs across multiple servers, setup alarms, custom counters, and realtime monitor their application.


Usage
-----
Modify your Config.groovy file to add an appender to log4j.

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

Grails Artefact Methods
-----------------------

The following methods are available in all Domain Classes, Controllers, and Services

```groovy
	oohlaCount(counterName, increment = 1)

	oohlaLog(level, message, category = [artifact name], exceptionOrStringDetails = null, timestamp = System.currentTimeMillis() )
```
These methods bypass Log4J level threshold checks.

Example:
```groovy
class myService {
	def myMethod() {
        oohlaLog('debug','interesting!') //always sent to OohLaLog
        oohlaCount('myMethod.called')
	}

	def myOtherMethod() {
        log.debug('toooo interesting!') // not sent to OohLaLog is threshold is INFO (as above)
	}

}
```

To use these methods please configure the OohLaLoh log4j appender as mentioned above or add the following Config.groovy setting:

```groovy
	oohlalog.authToken='XXXX-XXXX-XXXXXXX'
```


Additional Configuration Options
--------------------------------

You may also enable Controller Action counters via configuration:

```groovy
	oohlalog.webtransactions = true // globally enables counting 
	oohlalog.webtransactions.myController = true // enables counting for a specific controller
	oohlalog.webtransactions.myController = 'MyCtrlr' // enables counting for a specific controller with a custom counter namespace
	oohlalog.webtransactions.myController.myAction = true // enables counting for a specific controller action the default counter name
	oohlalog.webtransactions.myController.myAction = 'myAct' // enables counting for a specific controller action with a custom counter namespace
```

To use these configurations please configure the OohLaLoh log4j appender or Config setting as mentioned above.

Annotation Support
------------------

You may also enable controler Controller Action counters via Annotation. The Annotation can be used for the Controller class and the Controller Actions:


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


OohLaLog TagLib
---------------

Therea re three tags available:

olo:lib - adds and configures the JavaScript library
olo:counterImage - adds an image that will increment a counter when loaded by a client
olo:logImage - adds an image that will submit a log when loaded by a client

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





