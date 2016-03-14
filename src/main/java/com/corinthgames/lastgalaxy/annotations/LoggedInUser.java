package com.corinthgames.lastgalaxy.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggedInUser {

	/**
	 * Whether or not the method throws an exception if the user resolves
	 * to a null user. Defaults to true.
	 * @return
	 */
	boolean required() default true;
}
