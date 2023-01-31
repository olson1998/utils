package com.olson1998.messaging.domain.model;

public interface Message<T> {

    String getId();

    T getPayload();
}
