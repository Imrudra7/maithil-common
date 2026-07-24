package in.maithilart.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PublishMaithilEvent {

	String eventType();

	String entityType() default "";

	String entityIdField() default "";
	
	String correlationIdField() default "";

}