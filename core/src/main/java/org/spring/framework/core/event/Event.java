package org.spring.framework.core.event;

public enum Event {

    STARTING(1);

    Event(int type) {
        this.type = type;
    }

    int type;

}
