package com.github.rabbitnoteeth.bedrock.util.validation;

import com.github.rabbitnoteeth.bedrock.util.validation.annotation.Validate;
import com.github.rabbitnoteeth.bedrock.util.validation.entity.Rule;
import com.github.rabbitnoteeth.bedrock.util.validation.exception.ValidationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static void validate(Object bean) throws ValidationException, Exception {
        if (bean == null) {
            return;
        }
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(bean);
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (!(annotation instanceof Validate)) {
                    continue;
                }
                validate((Validate) annotation, value);
            }
        }
    }

    public static void validate(Validate annotation, Object obj) throws ValidationException, Exception {
        Rule rule = annotation.rule();
        String message = annotation.message();
        switch (rule) {
            case NOT_NULL -> {
                if (obj == null) {
                    throw new ValidationException(message);
                }
            }
            case NOT_BLANK -> {
                if (obj == null) {
                    throw new ValidationException(message);
                }
                if ((obj instanceof String) && ((String) obj).isEmpty()) {
                    throw new ValidationException(message);
                }
            }
            case null, default -> {}
        }
    }

}
