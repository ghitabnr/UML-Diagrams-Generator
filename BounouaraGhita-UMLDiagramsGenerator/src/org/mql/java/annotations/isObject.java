
package org.mql.java.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//To Check if a field is of type Object
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface isObject {
	boolean isObj() default true;
	String type() default "";

}


