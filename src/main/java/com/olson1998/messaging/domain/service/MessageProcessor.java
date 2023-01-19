package com.olson1998.messaging.domain.service;

import com.olson1998.messaging.domain.model.Inbound;

public interface MessageProcessor<P> {

    void process(Inbound<P> inbound);
}
