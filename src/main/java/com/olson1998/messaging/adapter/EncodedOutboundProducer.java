package com.olson1998.messaging.adapter;

import com.olson1998.messaging.domain.model.Acknowledgment;
import com.olson1998.messaging.domain.model.EncodedMessage;
import com.olson1998.messaging.domain.model.Outbound;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface EncodedOutboundProducer<O extends Outbound<String>, M extends EncodedMessage>{

    void send(O outbound);

    void send(Collection<O> outbounds);

    Acknowledgment<M, String> sendAcknowledged(O outbound);

    Collection<Acknowledgment<M, String>> sendAcknowledged(Collection<O> outbounds);

    CompletableFuture<Acknowledgment<M, String>> sendAsync(O outbound);

    CompletableFuture<Collection<Acknowledgment<M, String>>> sendAsync(Collection<O> outbounds);
}
