package com.olson1998.messaging.domain.model;

public interface Outbound<P> {

    String getId();
    String getTopic();

    P getPayload();

    long getSendingTime();

}
