package com.olson1998.messaging.domain.model;

public interface Outbound<P> {

    String getId();

    String getCommand();

    String getTenantKey();

    String getClaimingService();

    String getTopic();

    P getPayload();

    long getReceivingTime();

    long getProcessingTime();
}
