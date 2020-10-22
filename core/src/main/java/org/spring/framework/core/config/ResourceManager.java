package org.spring.framework.core.config;

import java.io.IOException;
import java.util.Map;

public interface ResourceManager {

    Map<String, String> loadProperties() throws IOException;

}
