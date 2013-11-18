package com.oohlalog.grails

class OohLaLogTagLib {
	def oohLaLogService
	def oohlalog = {attrs, body->
		def host = attrs.remove('host') ?: 'http://app.oohlalog.com',
			version = attrs.remove('version') ?: '0.1',
			minified = attrs.remove('minified'),
			apd = oohLaLogService.getOohLaLogAppender()
		minified = minified != 'false'

		def script = 'olo-'+version+(minified ? '.min' : '')+'.js'
		out << ('<script src="'+host+'/lib/'+script+'" type="text/javascript"></script>\n<script>\nolo.setApiKey("'+apd.authToken+'");\nolo.setHost("'+apd.host+':'+apd.port+'");\nolo.setProtocol("'+(apd.secure?'https':'http')+'");\n</script>')
	}
}