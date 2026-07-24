package in.maithilart.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {

	boolean input() default true;

    boolean output() default true;
    
    boolean logExceptions() default true;
}
