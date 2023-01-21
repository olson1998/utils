package com.olson1998.messaging.adapter;

import com.olson1998.messaging.domain.model.Message;
import com.olson1998.messaging.domain.model.Outbound;

public interface EncodedOutboundProducer extends OutboundProducer<Outbound<String>, Message<String>, String>{

}
