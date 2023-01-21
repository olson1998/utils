package com.olson1998.messaging.adapter;

import com.olson1998.messaging.domain.model.Acknowledgment;
import com.olson1998.messaging.domain.model.Message;
import com.olson1998.messaging.domain.model.Outbound;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface OutboundProducer<O extends Outbound<P>, M extends Message<P>, P> {

    void send(O outbound);

    void send(Collection<O> outbounds);

    Acknowledgment<M, P> sendAcknowledged(O outbound);

    Collection<Acknowledgment<M, P>> sendAcknowledged(Collection<O> outbounds);

    CompletableFuture<Acknowledgment<M, P>> sendAsync(O outbound);

    Collection<CompletableFuture<Acknowledgment<M, P>>> sendAsync(Collection<O> outbounds);
}
