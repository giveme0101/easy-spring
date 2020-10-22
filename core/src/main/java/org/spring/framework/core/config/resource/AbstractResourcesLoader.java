package org.spring.framework.core.config.resource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AbstractResourcesLoader
 * @Date 2020/10/22 14:32
 */
public abstract class AbstractResourcesLoader implements ResourceLoader {

    protected File property;
    protected ResourceLoader next;

    @Override
    public void setNext(ResourceLoader next) {
        this.next = next;
    }

    @Override
    public Map<String, String> loadProperty(File file) throws IOException {

        if (!isMatch(file)) {
            if (next != null) {
                return next.loadProperty(file);
            }

            return new HashMap<>(0);
        }

        this.property = file;
        return loadProperty();
    }

    abstract Map<String, String> loadProperty() throws IOException;

}
