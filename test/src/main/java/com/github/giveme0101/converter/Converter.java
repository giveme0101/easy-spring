package com.github.giveme0101.converter;

public interface Converter<BO, DO> {

    BO convertToBO(DO _do);

    DO convertToDO(BO _bo);

}
