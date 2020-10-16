package org.spring.framework.core.context;

import org.spring.framework.core.bean.BeanFactory;
import org.spring.framework.core.CommandLineRunner;
import org.spring.framework.core.event.Event;
import org.spring.framework.core.event.EventListener;
import org.spring.framework.core.util.ContextLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AbstractApplicationContext
 * @Date 2020/09/17 9:11
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    protected List<EventListener> eventObservableList = new ArrayList<>();

    protected String defaultPropertiesLocation = "application.properties";

    protected final Properties properties;

    public AbstractApplicationContext() {
        ContextLoader.put(this);
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
    public <T> Map<String, T> getBeansOfType(Class<T> beanClass) {
        return getBeanFactory().getBeansOfType(beanClass);
    }

    @Override
    public void refresh() {
        onRefresh();
        this.publish(Event.STARTING);
        this.getBeansOfType(CommandLineRunner.class).values().forEach(runner -> runner.run());
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
        Optional.ofNullable(eventObservableList).ifPresent(list -> {
            for (final EventListener observable : list) {
                try {
                    observable.onEvent(event);
                } catch (Exception ex){}
            }
        });
    }

    @Override
    public void addListener(EventListener eventListener) {
        this.eventObservableList.add(eventListener);
    }

    @Override
    public void removeListener(EventListener eventListener) {
        eventObservableList.remove(eventListener);
    }

    protected abstract BeanFactory getBeanFactory();

    protected abstract void refreshBeanFactory();

}
