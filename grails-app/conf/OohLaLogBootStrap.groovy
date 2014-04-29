class OohLaLogBootStrap {
    def init = { servletContext ->
    	try {
	    	Class.forName('grails.plugin.springsecurity.SpringSecurityUtils').clientRegisterFilter('oohLaLogAuthFilter', Integer.MAX_VALUE - 10)
    	} catch (java.lang.ClassNotFoundException e) {}
    }
}