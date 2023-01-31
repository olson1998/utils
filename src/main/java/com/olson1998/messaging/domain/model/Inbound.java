package com.olson1998.messaging.domain.model;

import java.util.Map;

public interface Inbound<P> {

    String getId();

    P getPayload();

    Map<String, String> getHeaders();

    long getSendingTime();

    long getReceivingTime();
}
