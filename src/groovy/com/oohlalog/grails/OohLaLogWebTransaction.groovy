package com.oohlalog.grails

import java.lang.annotation.*

@Target([ElementType.FIELD, ElementType.METHOD, ElementType.TYPE])
@Retention(RetentionPolicy.RUNTIME)
public @interface OohLaLogWebTransaction {
	String value()
}