package com.github.rabbitnoteeth.bedrock.util.bean;

import com.github.rabbitnoteeth.bedrock.util.StringUtils;
import com.github.rabbitnoteeth.bedrock.util.bean.annotation.Bean;
import com.github.rabbitnoteeth.bedrock.util.bean.annotation.BeanProvider;
import com.github.rabbitnoteeth.bedrock.util.bean.exception.BeanAssemblyException;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeanContainer {

    private BeanContainer() {
    }

    public static void scan(String basePackage) throws BeanAssemblyException {
        try {
            if (StringUtils.isBlank(basePackage)) {
                throw new IllegalArgumentException("the parameter can not be blank");
            }
            Reflections reflections = new Reflections(basePackage);
            Set<Class<?>> classes = new HashSet<>();
            classes.addAll(reflections.getTypesAnnotatedWith(Bean.class));
            classes.addAll(reflections.getTypesAnnotatedWith(BeanProvider.class));
            BeanFactory.assemble(classes);
        } catch (BeanAssemblyException e) {
            throw e;
        } catch (Throwable e) {
            throw new BeanAssemblyException(e);
        }
    }

    public static <T> T getBeanByName(String name) {
        return BeanFactory.getBeanByName(name);
    }

    public static <T> List<T> getBeansByType(Class<T> class_) {
        return BeanFactory.getBeansByType(class_);
    }

    public static List<Object> getBeansByAnnotation(Class<? extends Annotation> annotation) {
        return BeanFactory.getBeansByAnnotation(annotation);
    }

}
