import com.oohlalog.log4j.*

//oohlalog.webtransactions.oohLaLogTestController.test2 = 'testTwo'
oohlalog.webtransactions = true

log4j = {
	error	'org.codehaus.groovy.grails.web.servlet',  //  controllers
				'org.codehaus.groovy.grails.web.pages', //  GSP
				'org.codehaus.groovy.grails.web.sitemesh', //  layouts
				'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
				'org.codehaus.groovy.grails.web.mapping', // URL mapping
				'org.codehaus.groovy.grails.commons', // core / classloading
				'org.codehaus.groovy.grails.plugins', // plugins
				'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
				'org.springframework',
				'org.hibernate',
				'net.sf.ehcache.hibernate',
				'grails.util.GrailsUtil'
	appenders {
	    console name:'stdout', layout: pattern(conversionPattern: '%d{DATE} | %p | %c{1}:%L | %m%n')
	    rollingFile name: 'grailsLog', maxFileSize: (1 * 1024 * 1024), file: 'app.log', maxBackupIndex: 1,
	        layout: pattern(conversionPattern: '%d{yyyy-MM-dd HH:mm:ss.SSS} | %p | %c | %t | %m | %x%n')
	    rollingFile name: 'stacktrace', maxFileSize: (1 * 1024 * 1024), file: 'stacktrace.log', maxBackupIndex: 1,
	        layout: pattern(conversionPattern: '%d{yyyy-MM-dd HH:mm:ss.SSS} | %p | %c | %t | %m | %x%n')
	    appender new OohLaLogAppender( name: "oohlalog", authToken: "1adec358-09bd-49b5-834c-8343c3034a9e", maxBuffer:3,debug:true)
	}
	root {
	info 'stdout', 'grailsLog', 'oohlalog'
	additivity = true
	}
}
