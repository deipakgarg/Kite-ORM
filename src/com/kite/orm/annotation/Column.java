package com.kite.orm.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation is used for marking Entity's Field as a column of Data Model.
 * 
 * @author Deepak Garg
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column
{
	/**
	 * Put column name in this field.
	 */
	String name();
	
	/**
	 * set sql type in detail in this field.
	 * for eg: VARCHAR(50) NOT NULL
	 */
	String sqlType() default "";
	
	/**
	 * set true|false : default false.
	 */
	boolean nullable() default false;
}
