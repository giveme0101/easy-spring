package org.spring.framework.core.context;

import org.spring.framework.core.beans.BeanFactory;
import org.spring.framework.core.event.ApplicationEventPublish;

public interface ApplicationContext extends BeanFactory, ApplicationEventPublish {

    ApplicationContext run(Class<?>... configClass);

    void close();

}
