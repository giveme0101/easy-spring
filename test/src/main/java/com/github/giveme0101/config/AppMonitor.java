package com.github.giveme0101.config;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.annotation.Component;
import org.spring.framework.core.CommandLineRunner;
import org.spring.framework.core.beans.DisposableBean;
import org.spring.framework.core.event.Event;
import org.spring.framework.core.event.EventListener;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name AppMonitor
 * @Date 2020/10/15 11:20
 */
@Slf4j
@Component
public class AppMonitor implements CommandLineRunner, EventListener, DisposableBean {

    @Override
    public void run(String... args) {
        log.debug("CommandLineRunner.run");
    }

    @Override
    public void onEvent(Event event) {
        log.debug("event: {}", event);
    }

    @Override
    public void destroy() {
        log.debug("DisposableBean.destroy");
    }
}
