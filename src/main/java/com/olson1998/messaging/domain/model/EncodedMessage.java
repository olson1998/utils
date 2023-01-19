package com.olson1998.messaging.domain.model;

public interface EncodedMessage extends Message<String> {

    @Override
    String getPayload();

}
