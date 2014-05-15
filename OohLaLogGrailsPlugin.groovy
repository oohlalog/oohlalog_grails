import com.oohlalog.grails.OohLaLogService as OLLS

import com.oohlalog.grails.OohLaLogWebTransaction
import com.oohlalog.grails.OohLaLogFilters
import com.oohlalog.grails.OohLaLogAuthenticationFilter

class OohLaLogGrailsPlugin {
	def version         = "0.3.4"
	def grailsVersion   = "2.0 > *"
	def title           = "OohLaLog Plugin" // Headline display name of the plugin
	def organization    = [ name: "OohLaLog", url: "http://www.oohlalog.com/" ]
	def description     = "Grails Oohlalog Log4j Appender for Passing logs to the oohlalog API."
	def documentation   = "https://github.com/oohlalog/oohlalog_grails"
	def issueManagement = [ system: "GITHUB", url: "http://github.com/oohlalog/oohlalog_grails/issues" ]
	def developers      = [ [ name: "Jeremy Leng", email: "jleng@bcap.com" ], [ name: "Brian Wheeler", email: "bwheeler@bcap.com" ], [ name: "David Estes", email: "destes@bcap.com" ], [ name: "Jordon Saardchit", email: "jsaardchit@bcap.com" ]]
	def scm             = [ url: "https://github.com/oohlalog/oohlalog_grails" ]
	def license         = "APACHE"
	def loadBefore      = ['database-migration']
    def loadAfter       = ['controllers', 'services', 'spring-security-core']
    def pluginExcludes = [
        "grails-app/views/error.gsp",
        "grails-app/controllers/com/oohlalog/test/*",
        "grails-app/domain/com/oohlalog/test/*",
        "grails-app/services/com/oohlalog/test/*"
    ]
    def watchedResources = [
        "file:./grails-app/conf/*Filters.groovy",
        "file:./grails-app/services/*.groovy",
        "file:./grails-app/domain/*.groovy",
        "file:./grails-app/controller/*.groovy"
    ]
    private getArgs(ct) {
        (1..ct).collect { Object.class }.toArray()
    }

    def doWithSpring = {
        oohLaLogAuthFilter(OohLaLogAuthenticationFilter)

        for(service in application.serviceClasses) {
            if (service.logicalPropertyName != 'oohLaLog') {
	        	//println 'adding methods to ' + service.logicalPropertyName+'Service'
            	OLLS.LOGGING_METHODS.each {k, v-> if (!service.hasMetaMethod(k, getArgs(v))) service.metaClass."${k}" = OLLS."${k}" }
            }
        }
        OohLaLogFilters.clearWebTransactions()
        def configDoAll = false,
            requestURI = false
        if (application.config.oohlalog.containsKey('webtransactions') ) {
        	if (application.config.oohlalog.webtransactions == true) configDoAll = true
            if (application.config.oohlalog.requestURI == true) requestURI = true
        	else if (application.config.oohlalog.webtransactions instanceof Map) {
        		application.config.oohlalog.webtransactions.each {k, v->
        			if (v == true) {
        				OohLaLogFilters.addWebTransaction(k, k)
        			} else if (v instanceof Map) {
        				v.each {k2,v2->
        					if (v2 instanceof String)
		        				OohLaLogFilters.addWebTransaction(k+'.'+k2, v2)
		        			else if (v2) {
		        				OohLaLogFilters.addWebTransaction(k+'.'+k2, k+'.'+k2)
		        			}
        				}
        			}
        		}
        	}
        }

        application.controllerClasses.each {controller->
            OLLS.LOGGING_METHODS.each {k, v-> if (!controller.hasMetaMethod(k, getArgs(v))) controller.metaClass."${k}" = OLLS."${k}" }
            def doAll = configDoAll, doAllName = controller.logicalPropertyName + 'Controller'
            if (doAll == false && controller.hasProperty('oohLaLogCountActions')) {
                doAll = (controller.getPropertyValue('oohLaLogCountActions') == true)
			} else if (doAll == false  && controller.clazz.isAnnotationPresent(OohLaLogWebTransaction) ) {
				doAll = true
                doAllName = controller.clazz.getAnnotation(OohLaLogWebTransaction).value() ?: doAllName
			}

            controller.clazz.declaredFields.each {field->
            	if (field.isAnnotationPresent(grails.web.Action)) {
	            	OohLaLogWebTransaction a = (OohLaLogWebTransaction)field.getAnnotation(OohLaLogWebTransaction)
	//            	println 'FIELD checking '+field.name+' ' + field.isAnnotationPresent(OohLaLogWebTransaction) + ' annotation '+ field.getAnnotations()
	            	if (doAll || a) {
	            		def k = controller.logicalPropertyName + 'Controller.' + field.name
						if (!OohLaLogFilters.hasWebTransaction(k)) OohLaLogFilters.addWebTransaction(k, a?.value() ?: (requestURI ? 'REQUESTED_URL' : (doAllName+'.'+field.name)))
	            	}
	            }
            }

            controller.clazz.declaredMethods.each {method->
            	if (method.isAnnotationPresent(grails.web.Action)) {
	            	OohLaLogWebTransaction a = (OohLaLogWebTransaction)method.getAnnotation(OohLaLogWebTransaction)
	            	//println 'METHOD checking '+method.name+' ' + method.isAnnotationPresent(OohLaLogWebTransaction) + ' annotation '+ method.getAnnotations()
	            	if (doAll || a) {
	            		def k = controller.logicalPropertyName + 'Controller.' + method.name
						if (!OohLaLogFilters.hasWebTransaction(k)) OohLaLogFilters.addWebTransaction(k, a?.value() ?: (requestURI ? 'REQUESTED_URL' : (doAllName+'.'+method.name)))
	            	}
            	}
            }
        }
        for(domain in application.domainClasses) {
            OLLS.LOGGING_METHODS.each {k, v-> if (!domain.hasMetaMethod(k, getArgs(v))) domain.metaClass."${k}" = OLLS."${k}" }
        }
//        println OohLaLogFilters.CONFIGURED_CONTROLLER_NAMES

    }

    def onChange = { event ->
    	if (event.source) {
    		doWithSpring()
    	}
    }

	def onConfigChange = { event ->
    	if (event.source) {
    		doWithSpring()
    	}
	}


}
