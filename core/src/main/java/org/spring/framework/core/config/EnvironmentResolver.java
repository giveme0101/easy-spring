package org.spring.framework.core.config;

import java.util.Properties;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name EnvironmentResolver
 * @Date 2020/10/14 13:48
 */
public interface EnvironmentResolver {

    Properties getProperties();

    String getValue(String key);

}
