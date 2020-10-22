package org.spring.framework.core.context;

import org.spring.framework.core.beans.BeanFactory;
import org.spring.framework.core.EnvironmentResolver;
import org.spring.framework.core.PropertiesLoader;
import org.spring.framework.core.event.ApplicationEventPublish;

public interface ApplicationContext extends BeanFactory, EnvironmentResolver, PropertiesLoader, ApplicationEventPublish {

    void close();

}
