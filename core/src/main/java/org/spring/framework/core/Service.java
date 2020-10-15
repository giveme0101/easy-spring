package org.spring.framework.core;

import java.lang.annotation.*;

@Component
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {

//    String value() default "";

}
