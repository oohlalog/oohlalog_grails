package com.oohlalog.test

class OohLaLogTestService {
	def testMethod() {
        oohlaLog('debug','testMethod')
        oohlaCount('testMethod')

	}

	def testMethod2() {
        oohlaLog('debug','testMethod2', 'MyCat')
        oohlaCount('testMethod')

	}

}