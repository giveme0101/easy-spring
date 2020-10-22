package org.spring.framework.core.config;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.config.resource.ResourceLoader;
import org.spring.framework.core.config.resource.PropertiesLoader;
import org.spring.framework.core.config.resource.YmlLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name ResourceManagerImpl
 * @Date 2020/10/22 15:15
 */
@Slf4j
public class ResourceManagerImpl implements ResourceManager {

    private Map<String, String> resourcesProperties = new HashMap<>();

    private static final String PROPERTIES_PREFIX = "application";

    @Override
    public Map<String, String> loadProperties() throws IOException {

        if (resourcesProperties.isEmpty()) {

            ResourceLoader ymlLoader = new YmlLoader();
            ymlLoader.setNext(new PropertiesLoader());

            try {
                URL url = this.getClass().getClassLoader().getResource("");
                File[] files = new File(url.toURI()).listFiles(pathname -> pathname.getName().startsWith(PROPERTIES_PREFIX));

                for (final File property : files) {
                    resourcesProperties.putAll(ymlLoader.loadProperty(property));
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                System.exit(-1);
            }
        }

        return ImmutableMap.copyOf(resourcesProperties);
    }
}
