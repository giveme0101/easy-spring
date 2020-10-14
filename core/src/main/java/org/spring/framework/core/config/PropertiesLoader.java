package org.spring.framework.core.config;

import java.io.IOException;
import java.util.Properties;

public interface PropertiesLoader {

    Properties getProperties(String location) throws IOException;

}
