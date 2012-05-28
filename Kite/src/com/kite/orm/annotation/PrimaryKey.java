package com.kite.orm.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation is used for declaring Primary of Entity.
 * This annotation is not mandatory for Entity.
 * 
 * @author Deepak Garg
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PrimaryKey
{
	/**
	 * field name of Entity, which you want to make primary key.
	 */
	String key();
	
	/**
	 * set autoIncrement property of Primary Key.
	 * eg: true|false
	 */
	boolean autoIncrement() default true;
}
