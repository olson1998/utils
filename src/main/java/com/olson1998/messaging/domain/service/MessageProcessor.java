package com.olson1998.messaging.domain.service;

import com.olson1998.messaging.domain.model.Inbound;
import com.olson1998.messaging.domain.model.Message;

public interface MessageProcessor<I extends Inbound<M, P>, M extends Message<P>, P>  {

    void process(I inbound);
}
