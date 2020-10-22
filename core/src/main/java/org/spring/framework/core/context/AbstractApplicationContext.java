package org.spring.framework.core.context;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.CommandLineRunner;
import org.spring.framework.core.beans.BeanFactory;
import org.spring.framework.core.event.Event;
import org.spring.framework.core.event.EventListener;
import org.spring.framework.core.util.BannerPrinter;
import org.spring.framework.core.util.ContextLoader;

import java.util.*;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AbstractApplicationContext
 * @Date 2020/09/17 9:11
 */
@Slf4j
public abstract class AbstractApplicationContext implements ApplicationContext {

    protected List<EventListener> eventObservableList = new ArrayList<>();

    private Thread shutdownHook;

    public AbstractApplicationContext() {
        BannerPrinter.print();
        ContextLoader.put(this);
        registerShutdownHook();
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
    public void publish(Event event) {
        Optional.ofNullable(eventObservableList).ifPresent(list -> {
            for (final EventListener observable : list) {
                try {
                    observable.onEvent(event);
                } catch (Exception ex) {
                }
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

    public void registerShutdownHook() {
        if (this.shutdownHook == null) {
            this.shutdownHook = new Thread() {
                @Override
                public void run() {
                    doClose();
                }
            };
            Runtime.getRuntime().addShutdownHook(this.shutdownHook);
        }
    }

    protected void doClose() {

        publish(Event.CLOSEING);

         destroyBeans();

        // closeBeanFactory();

        // onClose();

    }

    protected void destroyBeans() {
        destroySingletons();
    }

    @Override
    public void destroySingletons() {
        getBeanFactory().destroySingletons();
    }

}