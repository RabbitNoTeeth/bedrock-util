package com.github.rabbitnoteeth.bedrock.util.bean.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanProvider {

    String value() default "";

}
