package com.jdbc.ladder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used for marking Dto as a Persist Entity as per Data Model.
 * 
 * @author Deepak Garg
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table
{
	/**
	 * Put table name in this field.
	 */
	String name();
	
	/**
	 * Put database name in this field.
	 */
	String databaseName() default "";
}
