package com.github.convert;

@FunctionalInterface
public interface Converter {

    void convert(Object source, Object target);

}
