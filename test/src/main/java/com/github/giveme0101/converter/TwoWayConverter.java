package com.github.giveme0101.converter;

public interface TwoWayConverter<BO, DO> {

    default BO convertToBO(DO _do){return null;}

    default DO convertToDO(BO _bo){return null;}

}
