package org.spring.framework.core.event;

public interface ApplicationEventPublish {

    void addListener(EventListener eventListener);

    void removeListener(EventListener eventListener);

    void publish(Event event);

}
