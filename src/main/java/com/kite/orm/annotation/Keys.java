package com.kite.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used for defining Constraints on Entity.
 * 
 * @author Deepak Garg
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Keys
{
	/**
	 * Constraint for Unique key
	 */
	UniqueKey[] uniqueKey() default {};

	/**
	 * Constraint for Foreign key
	 */
	ForeignKey[] foreignKey() default {};
}
