package org.spring.framework.core.context;

import org.spring.framework.core.bean.BeanFactory;
import org.spring.framework.core.config.EnvironmentResolver;
import org.spring.framework.core.config.PropertiesLoader;
import org.spring.framework.core.event.ApplicationEventPublish;

public interface ApplicationContext extends BeanFactory, EnvironmentResolver, PropertiesLoader, ApplicationEventPublish {

    void close();

}
