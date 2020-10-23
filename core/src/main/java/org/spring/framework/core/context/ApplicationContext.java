package org.spring.framework.core.context;

import org.spring.framework.core.beans.BeanFactory;
import org.spring.framework.core.event.ApplicationEventPublish;

public interface ApplicationContext extends BeanFactory, ApplicationEventPublish {

    void close();

    ApplicationContext run(Class<?>... configClass);

}
