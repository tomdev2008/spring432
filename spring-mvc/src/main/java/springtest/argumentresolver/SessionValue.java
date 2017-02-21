package springtest.argumentresolver;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//标记自己注入值
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME) 
@Documented
public @interface SessionValue {
	String value() default "";
}
