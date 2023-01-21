package com.olson1998.messaging.adapter;

import com.olson1998.messaging.domain.model.Inbound;
import com.olson1998.messaging.domain.model.Message;

public class ReceivedInbound<P> implements Inbound<P> {

    private final String topic;

    private final String replyTopic;

    private final String tenantKey;

    private final long receivingTime;

    private String id;

    private String command;

    private String claimingService;

    private P payload;

    @Override
    public long getReceivingTime() {
        return receivingTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTenantKey() {
        return tenantKey;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getClaimingService() {
        return claimingService;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public String getReplyTopic() {
        return replyTopic;
    }

    @Override
    public P getPayload() {
        return payload;
    }

    protected ReceivedInbound(String topic, String replyTopic, String tenantKey, Message<P> message) {
        this.receivingTime = System.currentTimeMillis();
        this.topic = topic;
        this.tenantKey = tenantKey;
        this.replyTopic = replyTopic;
        if(message != null ){
            this.id= message.getId();
            this.command = message.getCommand();
            this.payload = message.getPayload();
            this.claimingService = message.getClaimingService();
        }
    }
}
