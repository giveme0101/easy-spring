package org.spring.framework.core.context;

import org.spring.framework.core.bean.BeanFactory;

public interface ApplicationContext extends BeanFactory {

    void close();

}
