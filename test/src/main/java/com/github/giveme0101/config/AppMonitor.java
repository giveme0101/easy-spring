package com.github.giveme0101.config;

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
@Component
public class AppMonitor implements CommandLineRunner, EventListener {

    @Override
    public void run(String... args) {
        System.out.println("App start up");
    }

    @Override
    public void onEvent(Event event) {
        System.out.println("APP STARTING...");
    }
}
