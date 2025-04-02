package com.github.bedrock.util.bean;

import com.github.bedrock.util.StringUtils;
import com.github.bedrock.util.bean.annotation.Autowired;
import com.github.bedrock.util.bean.annotation.Bean;
import com.github.bedrock.util.bean.annotation.BeanProvider;
import com.github.bedrock.util.bean.exception.BeanAssemblyException;
import com.github.bedrock.util.bean.exception.BeanCreationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class BeanFactory {

    private static final Map<String, Object> BEAN_MAP = new LinkedHashMap<>();
    private static final Map<String, BeanCreationTask> TASK_MAP = new LinkedHashMap<>();

    private BeanFactory() {
    }

    static void assemble(Set<Class<?>> classes) throws BeanAssemblyException {
        try {
            for (Class<?> class_ : classes) {
                Bean bean = class_.getDeclaredAnnotation(Bean.class);
                if (bean != null) {
                    String value = bean.value();
                    String beanName = StringUtils.isBlank(value) ? class_.getSimpleName() : value;
                    TASK_MAP.put(beanName, new BeanCreationTask(beanName, class_, null, null));
                    continue;
                }
                BeanProvider beanProvider = class_.getDeclaredAnnotation(BeanProvider.class);
                if (beanProvider != null) {
                    String value = beanProvider.value();
                    String beanName = StringUtils.isBlank(value) ? class_.getSimpleName() : value;
                    BeanCreationTask beanCreationTask = new BeanCreationTask(beanName, class_, null, null);
                    TASK_MAP.put(beanName, beanCreationTask);
                    Method[] methods = class_.getMethods();
                    if (methods.length == 0) {
                        continue;
                    }
                    for (Method method : methods) {
                        Bean bean_ = method.getDeclaredAnnotation(Bean.class);
                        if (bean_ == null) {
                            continue;
                        }
                        Class<?> returnType = method.getReturnType();
                        String value_ = bean_.value();
                        String beanName_ = StringUtils.isBlank(value_) ? returnType.getSimpleName() : value_;
                        TASK_MAP.put(beanName_, new BeanCreationTask(beanName_, returnType, method, beanCreationTask));
                    }
                }
            }
            for (Map.Entry<String, BeanCreationTask> entry : TASK_MAP.entrySet()) {
                String beanName = entry.getKey();
                BeanCreationTask beanCreationTask = entry.getValue();
                BEAN_MAP.putIfAbsent(beanName, beanCreationTask.execute());
            }
        } catch (Throwable e) {
            throw new BeanAssemblyException(e);
        }

    }

    static <T> List<T> getBeansByType(Class<T> class_) {
        return BEAN_MAP
            .values()
            .stream()
            .filter(obj -> class_.isAssignableFrom(obj.getClass()))
            .map(obj -> (T) obj)
            .collect(Collectors.toList());
    }

    static List<Object> getBeansByAnnotation(Class<? extends Annotation> annotation) {
        return BEAN_MAP
            .values()
            .stream()
            .filter(obj -> obj.getClass().isAnnotationPresent(annotation))
            .collect(Collectors.toList());
    }

    static <T> T getBeanByName(String name) {
        if (BEAN_MAP.containsKey(name)) {
            return null;
        }
        return (T) (BEAN_MAP.get(name));
    }

    private static class BeanCreationTask {
        final String beanName;
        final Class<?> beanType;
        final Method method;
        final BeanCreationTask parent;

        private BeanCreationTask(String beanName, Class<?> beanType, Method method, BeanCreationTask parent) {
            this.beanName = beanName;
            this.beanType = beanType;
            this.method = method;
            this.parent = parent;
        }

        public Object execute() throws BeanCreationException {
            try {
                if (BEAN_MAP.containsKey(this.beanName)) {
                    return BEAN_MAP.get(this.beanName);
                }
                Object result = method == null ? newInstance() : newInstanceByMethod();
                BEAN_MAP.put(this.beanName, result);
                return result;
            } catch (Throwable e) {
                throw new BeanCreationException("failed to create bean of [" + this.beanType.getTypeName() + "]", e);
            }
        }

        private Object newInstanceByMethod() throws Throwable {
            Object parent = this.parent.execute();
            Parameter[] parameters = this.method.getParameters();
            if (parameters.length == 0) {
                return method.invoke(parent);
            }
            Object[] args = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                Class<?> parameterType = parameter.getType();
                Autowired autowired = parameter.getDeclaredAnnotation(Autowired.class);
                String beanName;
                if (autowired != null && StringUtils.isNotBlank(autowired.value())) {
                    beanName = autowired.value();
                } else {
                    beanName = parameterType.getSimpleName();
                }
                Object argInstance = BEAN_MAP.containsKey(beanName) ? BEAN_MAP.get(beanName) :
                    (TASK_MAP.containsKey(beanName) ? TASK_MAP.get(beanName).execute() : null);
                args[i] = argInstance;
            }
            return method.invoke(parent, args);
        }

        private Object newInstance() throws Throwable {
            Constructor<?>[] constructors = beanType.getDeclaredConstructors();
            Constructor<?> constructor;
            if (constructors.length == 1) {
                constructor = constructors[0];
            } else {
                Optional<Constructor<?>> optional = Arrays.stream(constructors).filter(c -> c.isAnnotationPresent(Autowired.class)).findFirst();
                constructor = optional.orElse(constructors[0]);
            }
            constructor.setAccessible(true);
            Parameter[] parameters = constructor.getParameters();
            if (parameters.length == 0) {
                return constructor.newInstance();
            }
            Object[] args = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                Class<?> parameterType = parameter.getType();
                Autowired autowired = parameter.getDeclaredAnnotation(Autowired.class);
                String beanName;
                if (autowired != null && StringUtils.isNotBlank(autowired.value())) {
                    beanName = autowired.value();
                } else {
                    beanName = parameterType.getSimpleName();
                }
                Object argInstance = BEAN_MAP.containsKey(beanName) ? BEAN_MAP.get(beanName) :
                    (TASK_MAP.containsKey(beanName) ? TASK_MAP.get(beanName).execute() : null);
                Field[] fields = parameterType.getDeclaredFields();
                for (Field field : fields) {
                    Autowired autowiredOfField = field.getDeclaredAnnotation(Autowired.class);
                    if (autowiredOfField == null) {
                        continue;
                    }
                    Class<?> fieldType = field.getType();
                    beanName = StringUtils.isNotBlank(autowiredOfField.value()) ? autowiredOfField.value() : fieldType.getSimpleName();
                    field.setAccessible(true);
                    Object fieldInstance = BEAN_MAP.containsKey(beanName) ? BEAN_MAP.get(beanName) :
                        (TASK_MAP.containsKey(beanName) ? TASK_MAP.get(beanName).execute() : null);
                    field.set(argInstance, fieldInstance);
                }
                args[i] = argInstance;
            }
            return constructor.newInstance(args);
        }
    }

}
