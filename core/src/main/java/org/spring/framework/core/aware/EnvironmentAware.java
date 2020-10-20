package org.spring.framework.core.aware;

import java.util.Properties;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name EnvironmentAware
 * @Date 2020/10/20 15:20
 */
public interface EnvironmentAware extends Aware{

    void setProperties(Properties properties);

}