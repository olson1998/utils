package com.olson1998.messaging.adapter;

import com.olson1998.messaging.domain.model.Acknowledgment;
import com.olson1998.messaging.domain.model.EncodedOutbound;

import java.util.Collection;

public interface EncodedOutboundProducer<M extends EncodedOutbound<String>>{

    void send(M message);

    void send(Collection<M> messages);

    Acknowledgment<M> sendAsync(M message);

    Collection<Acknowledgment<M>> sendAsync(Collection<M> messages);
}
