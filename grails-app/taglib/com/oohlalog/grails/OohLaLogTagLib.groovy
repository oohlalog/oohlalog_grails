package com.oohlalog.grails

class OohLaLogTagLib {
	static namespace = "olo"
	def oohLaLogService

	def lib = {attrs, body->
		def version = attrs.remove('version') ?: '0.1',
			minified = attrs.remove('minified'),
			apd = oohLaLogService.getOohLaLogAppender()
		minified = minified != 'false'
		def script = 'olo-'+version+(minified ? '.min' : '')+'.js'
		out << ('<script src="'+(apd.secure?'https':'http')+'://'+apd.host+':'+apd.port+'/lib/'+script+'" type="text/javascript"></script>\n<script>\nolo.setApiKey("'+apd.authToken+'");\nolo.setHost("'+apd.host+':'+apd.port+'");\nolo.setProtocol("'+(apd.secure?'https':'http')+'");\n</script>')
	}
	def counterImage = {attrs, body->
		def name = attrs.remove('name') ?: 'myImageCounter',
			apd = oohLaLogService.getOohLaLogAppender()
		out << ('<img src="'+(apd.secure?'https':'http')+'://'+apd.host+':'+apd.port+'/api/count/image/'+apd.authToken+'/'+name.encodeAsURL()+'" class="oloCounterImage"/>')
	}
	def logImage = {attrs, body->
		def message = attrs.remove('message') ?: 'myMessage',
			category = attrs.remove('category') ?: 'myCategory',
			level = attrs.remove('level') ?: 'INFO',
			apd = oohLaLogService.getOohLaLogAppender()
		out << ('<img src="'+(apd.secure?'https':'http')+'://'+apd.host+':'+apd.port+'/api/log/image/'+apd.authToken+'/'+level.encodeAsURL()+'/'+category.encodeAsURL()+'/'+message.encodeAsURL()+ '" class="oloLogsImage"/>')
	}

}