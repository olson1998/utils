package com.olson1998.messaging.domain.model;

public interface Message<T> {

    String getId();

    String getClaimingService();

    String getCommand();

    T getPayload();
}
