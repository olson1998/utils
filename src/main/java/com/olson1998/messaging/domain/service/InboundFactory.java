package com.olson1998.messaging.domain.service;

import com.olson1998.messaging.domain.model.Inbound;
import com.olson1998.messaging.domain.model.Message;

public interface InboundFactory {

    <P> Inbound<P> fabricate(String topic, String tenantKey, long sendingTime, long receivingTime, Message<P> message);
}
