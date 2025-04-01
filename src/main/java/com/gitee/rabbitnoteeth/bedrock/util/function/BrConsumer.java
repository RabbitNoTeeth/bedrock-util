package com.gitee.rabbitnoteeth.bedrock.util.function;

@FunctionalInterface
public interface BrConsumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t) throws Throwable;

}
