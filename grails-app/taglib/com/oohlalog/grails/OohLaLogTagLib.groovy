package com.oohlalog.grails

class OohLaLogTagLib {
	def oohlalog = {attrs, body->
		def host = attrs.remove('host') ?: 'http://app.oohlalog.com',
			version = attrs.remove('version') ?: '0.1',
			minified = attrs.remove('minified')
		minified = minified != 'false'

		def script = 'olo-'+version+(minified ? '.min' : '')+'.js'
		out << ('<script src="'+host+'/lib/'+script+'" type="text/javascript"></script>')
	}
}