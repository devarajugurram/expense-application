package com.exp.backend.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * InterfaceName - messageInterface
 * This is a custom interface.
 * It is used in Aspect Oriented Programming for logging,security and transactional events.
 * Retention Policy is Runtime it will automatically apply in runtime.
 * Target are methods.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface messageInterface {
    /**
     * MethodName - value
     * @return String message need to be shown for the method which the current interface is applying.
     */
    String value() default "";
}
