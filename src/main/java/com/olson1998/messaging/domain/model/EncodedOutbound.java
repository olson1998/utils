package com.olson1998.messaging.domain.model;

public interface EncodedOutbound extends Message<String>{

    @Override
    String getPayload();
}
