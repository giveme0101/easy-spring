package com.github.giveme0101.config;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.Component;
import org.spring.framework.core.config.CommandLineRunner;
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
public class AppMonitor implements CommandLineRunner, EventListener {

    @Override
    public void run(String... args) {
        log.debug("CommandLineRunner");
    }

    @Override
    public void onEvent(Event event) {
        log.debug("event: {}", event);
    }
}
