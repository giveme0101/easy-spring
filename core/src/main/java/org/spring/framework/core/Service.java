package org.spring.framework.core;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Inherited
public @interface Service {

    String value() default "";

}
