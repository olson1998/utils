package com.olson1998.messaging.domain.model;

public interface Acknowledgment<M extends Message<P>, P> {

    String getTopic();

    int getPartition();

    boolean hasTimestamp();

    boolean hasOffset();

    long getTimestamp();

    long getOffset();

    int getSerializedKeySize();

    int getSerializedValueSize();

    M getSentMessage();
}
