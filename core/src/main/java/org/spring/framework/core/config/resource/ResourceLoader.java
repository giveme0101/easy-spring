package org.spring.framework.core.config.resource;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name ResourceLoader
 * @Date 2020/10/22 14:28
 */
public interface ResourceLoader {

    Map<String, String> loadProperty(File file) throws IOException;

    boolean isMatch(File file);

    void setNext(ResourceLoader next);

}
