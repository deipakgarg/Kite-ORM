package com.kite.orm.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation is used for defining Unique Constraint on Entity.
 * 
 * @author Deepak Garg
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface UniqueKey
{
	/**
	 * field name or array of field names.
	 */
	String[] keys();
}
