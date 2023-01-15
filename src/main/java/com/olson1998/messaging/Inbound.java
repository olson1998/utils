package com.olson1998.messaging;

public class Inbound<M extends MessagePattern> {

    private final String tenantKey;

    private final String topic;

    private final String replyTopic;

    private final M message;

    public String getTenantKey() {
        return tenantKey;
    }

    public String getTopic() {
        return topic;
    }

    public String getReplyTopic() {
        return replyTopic;
    }

    public M getMessage(){
        return message;
    }

    public Inbound(String tenantKey, String topic, String replyTopic, M message) {
        this.tenantKey = tenantKey;
        if(topic == null){
            throw new NullPointerException("topic can not be null");
        }else {
            this.topic = topic;
        }
        if(replyTopic == null ){
            throw new NullPointerException("reply topic can not be null");
        }else {
            this.replyTopic = replyTopic;
        }
        if(message == null){
            throw new NullPointerException("inbound message can not be null");
        }else {
            this.message = message;
        }
    }

    public Inbound(String tenantKey, String topic, M message) {
        this.tenantKey = tenantKey;
        if(topic == null){
            throw new NullPointerException("topic can not be null");
        }else {
            this.topic = topic;
        }
        this.replyTopic = null;
        if(message == null){
            throw new NullPointerException("inbound message can not be null");
        }else {
            this.message = message;
        }
    }
}
