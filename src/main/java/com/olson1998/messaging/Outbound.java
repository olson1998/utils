package com.olson1998.messaging;

public class Outbound<M extends MessagePattern> {

    private final String topic;

    private final M message;

    private final boolean async;

    private final boolean waitingForAcknowledgment;

    public String getTopic() {
        return topic;
    }

    public M getMessage() {
        return message;
    }

    public boolean isAsync() {
        return async;
    }

    public boolean isWaitingForAcknowledgment() {
        return waitingForAcknowledgment;
    }

    public Outbound(String topic, M message, boolean async, boolean waitingForAcknowledgment) {
        if(topic == null){
            throw new NullPointerException("topic can not be null");
        }else {
            this.topic = topic;
        }
        this.message = message;
        this.async = async;
        this.waitingForAcknowledgment = waitingForAcknowledgment;
    }

    public Outbound(String topic, M message, boolean async) {
        if(topic == null){
            throw new NullPointerException("topic can not be null");
        }else {
            this.topic = topic;
        }
        this.message = message;
        this.async = async;
        this.waitingForAcknowledgment = false;
    }

    public Outbound(String topic, M message) {
        if(topic == null){
            throw new NullPointerException("topic can not be null");
        }else {
            this.topic = topic;
        }
        this.message = message;
        this.async = false;
        this.waitingForAcknowledgment = false;
    }

    public Outbound(String topic) {
        if(topic == null){
            throw new NullPointerException("topic can not be null");
        }else {
            this.topic = topic;
        }
        this.message = null;
        this.async = false;
        this.waitingForAcknowledgment = false;
    }
}
