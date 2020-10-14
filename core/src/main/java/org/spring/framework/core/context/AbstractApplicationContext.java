package org.spring.framework.core.context;

import org.spring.framework.core.bean.BeanFactory;

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
public abstract class AbstractApplicationContext implements ApplicationContext {

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

    protected abstract BeanFactory getBeanFactory();

    protected abstract void refreshBeanFactory();

}
