package com.olson1998.messaging.domain.model;

import java.util.Map;

public interface Inbound<M extends Message<P>, P> {

    Map<String, String> getHeaders();

    M getMessage();

    long getSendingTime();

    long getReceivingTime();
}
