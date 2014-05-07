package com.kite.orm.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ForeignKey
{
	/**
	 * field name of Entity which you want to make foreign key reference.
	 */
	String key();
	
	/**
	 * name of foreign key reference column.
	 */
	String referenceColumn();
	
	/**
	 * name of foreign key reference table.
	 */
	String referenceEntity();
}
