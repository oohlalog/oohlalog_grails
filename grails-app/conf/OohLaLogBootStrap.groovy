import org.apache.commons.logging.LogFactory
class OohLaLogBootStrap {
	def grailsApplication
	private static final log = LogFactory.getLog(this)
    def init = { servletContext ->
    	try {
    		if (grailsApplication.config.oohlalog.springSecurityContext == true) {
    			try {
			    	def c = this.class.classLoader.loadClass('grails.plugin.springsecurity.SpringSecurityUtils')
			    	c.clientRegisterFilter('oohLaLogAuthFilter', Integer.MAX_VALUE - 10)
			    	log.info('Added OohLaLog SpringSecurity filter to capture principal token')
		    	} catch (java.lang.ClassNotFoundException  e) {
					log.error('Unable to add OohLaLog SpringSecurity filter to capture principal token. Check that Spring Security is installed.')
					//e.printStackTrace()
		    	}
    		}
    	} catch (ee) {
			log.error('Unable to add OohLaLog SpringSecurity filter to capture principal token: '+ee.toString())
		}
    }
}