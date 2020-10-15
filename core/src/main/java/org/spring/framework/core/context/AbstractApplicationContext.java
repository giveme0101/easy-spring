package org.spring.framework.core.context;

import org.spring.framework.core.bean.BeanFactory;
import org.spring.framework.core.event.ApplicationEventPublish;
import org.spring.framework.core.event.ApplicationEventPublisher;
import org.spring.framework.core.event.Event;
import org.spring.framework.core.event.EventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AbstractApplicationContext
 * @Date 2020/09/17 9:11
 */
public abstract class AbstractApplicationContext implements ApplicationContext, ApplicationEventPublish {

    protected ApplicationEventPublish applicationEventPublisher = new ApplicationEventPublisher();

    protected String defaultPropertiesLocation = "application.properties";

    protected final Properties properties;

    public AbstractApplicationContext() {
        try {
            properties = this.getProperties(defaultPropertiesLocation);
        } catch (IOException ex){
            throw new RuntimeException("unable to load " + defaultPropertiesLocation);
        }
    }

    @Override
    public Object getBean(String beanName) {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        return getBeanFactory().getBean(beanClass);
    }

    @Override
    public void refresh() {
        onRefresh();
    }

    protected void onRefresh() {

    }

    @Override
    public Properties getProperties(String location) throws IOException {

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(defaultPropertiesLocation);
        Properties prop = new Properties();
        prop.load(new InputStreamReader(inputStream));

        return prop;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public String getValue(String key) {
        return properties.getProperty(key);
    }

    @Override
    public void publish(Event event) {
        applicationEventPublisher.publish(event);
    }

    @Override
    public void addListener(EventListener eventListener) {
        applicationEventPublisher.addListener(eventListener);
    }

    @Override
    public void removeListener(EventListener eventListener) {
        applicationEventPublisher.removeListener(eventListener);
    }

    protected abstract BeanFactory getBeanFactory();

    protected abstract void refreshBeanFactory();

}
