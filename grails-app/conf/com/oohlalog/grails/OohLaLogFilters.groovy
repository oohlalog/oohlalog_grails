package com.oohlalog.grails

class OohLaLogFilters {
	static private CONFIGURED_CONTROLLER_NAMES = [:]

	static addWebTransaction(controllerActionName, transactionName) {
		println 'adding webtransaction ' + controllerActionName + '='+transactionName
		CONFIGURED_CONTROLLER_NAMES[controllerActionName] =  transactionName
	}

	static hasWebTransaction(controllerActionName) {
		return CONFIGURED_CONTROLLER_NAMES[controllerActionName] != null
	}

	static clearWebTransactions() {
		synchronized(CONFIGURED_CONTROLLER_NAMES) {
			CONFIGURED_CONTROLLER_NAMES.clear()	
		}
	}

    def filters = {
        all(controller:'*', action:'*') {
            after = { Map model ->
//            	println 'checking '+controllerName+' and '+controllerName+'.'+actionName
            	def transactionName = CONFIGURED_CONTROLLER_NAMES[controllerName+'.'+actionName] ?: CONFIGURED_CONTROLLER_NAMES[controllerName]
				if (transactionName) {
					OohLaLogService.oohlaCount(transactionName)
				}
            }
        }
    }
}
