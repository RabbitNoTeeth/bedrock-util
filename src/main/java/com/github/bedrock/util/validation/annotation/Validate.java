package com.github.bedrock.util.validation.annotation;

import com.github.bedrock.util.validation.entity.Rule;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validate {

    Rule rule();

    String message();

}
