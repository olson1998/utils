package com.olson1998.messaging.domain.model;

public interface Outbound<P> {

    String getId();

    String getTenantKey();

    String getCommand();

    String getClaimingService();

    String getTopic();

    String getReplyTopic();

    P getPayload();

    long getReceivingTime();

    long getProcessingTime();
}
