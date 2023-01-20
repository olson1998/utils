package com.olson1998.messaging.adapter;

import com.olson1998.messaging.domain.model.EncodedOutbound;

import java.util.Collection;

public abstract class EncodedOutboundProducer<M extends EncodedOutbound>{

    abstract void send(M message);

    abstract void send(Collection<M> messages);

}
