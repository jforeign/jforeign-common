package jforeign;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
public @interface Native {
    boolean lazy() default false;

    String inout() default "inout";

    String cc() default "";

    String charset() default "";
}
