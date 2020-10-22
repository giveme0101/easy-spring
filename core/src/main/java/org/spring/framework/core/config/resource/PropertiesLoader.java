package org.spring.framework.core.config.resource;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name PropertiesLoader
 * @Date 2020/10/22 14:34
 */
public class PropertiesLoader extends AbstractResourcesLoader {

    private static final String[] PROPERTIES_EXTENSIONS = new String[]{".properties"};

    @Override
    public Map<String, String> loadProperty() throws IOException {

        Properties prop = new Properties();
        prop.load(new InputStreamReader(new FileInputStream(this.getProperty())));

        Map<String, String> resultMap = new HashMap<>(prop.size());

        for (final Map.Entry<Object, Object> entry : prop.entrySet()) {
            resultMap.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }

        return resultMap;
    }

    @Override
    public boolean isMatch(File file) {

        boolean match = false;
        for (final String suffix : PROPERTIES_EXTENSIONS) {
            if (file.getName().endsWith(suffix)){
                match = true;
                break;
            }
        }

        return match;
    }


}
