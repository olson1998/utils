package com.olson1998.messaging.domain.model;

public interface Inbound<P> {

    String getId();

    P getPayload();

    long getSendingTime();

    long getReceivingTime();
}
