package org.spring.framework.core.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name ApplicationEventPublisher
 * @Date 2020/10/15 11:25
 */
public class ApplicationEventPublisher implements ApplicationEventPublish{

    private List<EventListener> eventObservableList = new ArrayList<>();

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

}
