package com.oohlalog.grails

import com.oohlalog.log4j.*
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.log4j.spi.LoggingEvent
import org.apache.log4j.Level
import org.apache.log4j.NDC
class OohLaLogService {
	private static appender = null

    static LOGGING_METHODS = [oohlaCount:2, oohlaLog:6, setOohlaLogToken:1	]

	static getOohLaLogAppender() {
		if (!appender) {
			//println 'looking for OohLaLogAppender'
			appender = Logger.rootLogger.getAllAppenders().find {
//				println it.class
				it instanceof OohLaLogAppender
			}
			if (!appender) {
				appender = new OohLaLogAppender()
				def conf = grails.util.Holders.grailsApplication.config.oohlalog
				['maxBuffer','submissionThreadPool','host','path','port','authToken','timeBuffer'].each {
					if (conf.containsKey(it)) appender."${it}" = conf[it] 
				}
			}
		}
		return appender
	}

	static oohlaCount = {counterName = null, int increment = 1 ->
//		println 'COUNTING '+counterName
		def apdr = OohLaLogService.getOohLaLogAppender(),
			logger = Logger.getLogger(counterName ?: delegate.class),
			loggingEvent = new LoggingEvent("org.apache.log4j.Logger",
				                            logger, 
				                            CountLevel.COUNT,
				                            'default', 
				                            null
				                            )
		loggingEvent.setProperty('increment', increment.toString())
		apdr.append(loggingEvent)

	}

	static setOohlaLogToken = {String ctx->
		org.apache.log4j.MDC.put('token', ctx)
	}

	static oohlaLog = {level, message, category = null, details = null, token = null, timestamp = System.currentTimeMillis() ->
		def apdr = OohLaLogService.getOohLaLogAppender(),
			logger = Logger.getLogger(category ?: delegate.class),
			loggingEvent = new LoggingEvent("org.apache.log4j.Logger",
				                            logger, 
				                            Level.toLevel(level.toUpperCase(), CountLevel.COUNT),
				                            message, 
				                            (details instanceof Throwable ? details : null)
				                            )

		if ( !(details instanceof Throwable) && details != null) loggingEvent.setProperty('details', details.toString())
		if ( timestamp != null) loggingEvent.setProperty('timestamp', timestamp.toString())
		if ( token != null) loggingEvent.setProperty('token', token)
		apdr.append(loggingEvent)
	}

}