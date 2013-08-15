class OohLaLogGrailsPlugin {
	def version         = "0.1-SNAPSHOT"
	def grailsVersion   = "2.0 > *"
	def title           = "OohLaLog Plugin" // Headline display name of the plugin
	def author          = "David Estes"
	def authorEmail     = "dev@oohlalog.com"
	def organization    = [ name: "OohLaLog", url: "http://www.oohlalog.com/" ]
	def description     = "Grails Oohlalog Log4j Appender for Passing logs to the oohlalog API."
	def documentation   = "https://github.com/oohlalog/oohlalog_grails"
	def issueManagement = [ system: "GITHUB", url: "http://github.com/oohlalog/oohlalog_grails/issues" ]
	def developers      = [ [ name: "Jeremy Leng", email: "jleng@bcap.com" ]]
	def scm             = [ url: "https://github.com/oohlalog/oohlalog_grails" ]
	def license         = "APACHE"
	def pluginExcludes = [
		"grails-app/views/error.gsp"
	]
}
