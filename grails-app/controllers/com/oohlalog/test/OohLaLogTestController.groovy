package com.oohlalog.test

import grails.converters.*
import com.oohlalog.grails.OohLaLogWebTransaction

class OohLaLogTestController {
	def oohLaLogTestService

    def index() {
        oohlaLog('debug','OohLaLogTestController.index')
        oohlaCount('poop',1000)
        render text:'test'
    }

    def taglib() {

    }

    @OohLaLogWebTransaction(name='testOne')
    def test1() {

        oohlaLog('debug','bingo1')
    	render text:'test1'
    }

    @OohLaLogWebTransaction(name='testTwo')
    def test2 = {
//        oohlaLog('debug','bingo2')

    	render text:'test2'
    }

    @OohLaLogWebTransaction(name='testThree')
    def test3() {
        oohlaLog('debug','bingo3')
        render text:'test3'
    }


    def test4() {
    	oohLaLogTestService.testMethod()
        render text:'test4'
    }

    def test5() {
    	oohLaLogTestService.testMethod2()
        render text:'test5'
    }

}
