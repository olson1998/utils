package com.olson1998.messaging.adapter;

import com.olson1998.messaging.domain.model.EncodedMessage;
import com.olson1998.messaging.domain.service.EncodedMessageProcessor;

public abstract class EncodedInboundListener<M extends EncodedMessage> {

    private final EncodedMessageProcessor messageProcessor;

    public void receive(String topic, String replyTopic, String tenantKey, M message){
        var inbound = new ReceivedInbound<>(topic, replyTopic, tenantKey, message);
        messageProcessor.process(inbound);
    }

    public EncodedInboundListener(EncodedMessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }
}
