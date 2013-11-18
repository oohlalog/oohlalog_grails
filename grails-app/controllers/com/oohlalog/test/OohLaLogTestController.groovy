package com.oohlalog.test

import grails.converters.*
import com.oohlalog.grails.OohLaLogWebTransaction

class OohLaLogTestController {
    def index() {
        oohlaLog('debug','OohLaLogTestController.index')
        oohlaCount('poop',1000)
        render text:'test'
    }

    def taglib() {
    	
    }

    @OohLaLogWebTransaction(name='testOne')
    def test1() {

    	render text:'test1'
    }

    @OohLaLogWebTransaction(name='testTwo')
    def test2 = {

    	render text:'test2'
    }

}
