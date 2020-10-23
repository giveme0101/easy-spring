package org.spring.framework.core.config;

import java.io.IOException;
import java.util.Map;

public interface ResourceManager extends StringValueResolver {

    Map<String, String> loadProperties() throws IOException;

}
