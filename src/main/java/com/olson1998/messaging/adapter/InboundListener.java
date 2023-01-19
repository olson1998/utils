package com.olson1998.messaging.adapter;

import com.olson1998.messaging.domain.model.EncodedMessage;
import com.olson1998.messaging.domain.model.Message;
import com.olson1998.messaging.domain.service.MessageProcessor;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class InboundListener<E extends EncodedMessage, M extends Message<P>, P> {

    private final MessageProcessor<P> messageProcessor;

    private final Function<E, M> messageDecodingFunction;

    private final BiFunction<Exception, E, M> decodingErrorHandler;

    public void receive(String topic, String replyTopic, String tenantKey, E message){
        var decoded = decodeMessage(message);
        var inbound = new ReceivedInbound<>(topic, replyTopic, tenantKey, decoded);
        messageProcessor.process(inbound);
    }

    private M decodeMessage(E message){
        try{
            return messageDecodingFunction.apply(message);
        }catch (Exception error){
            return decodingErrorHandler.apply(error, message);
        }
    }

    public InboundListener(MessageProcessor<P> messageProcessor,
                           Function<E, M> messageDecodingFunction,
                           BiFunction<Exception, E, M> decodingErrorHandler) {
        this.messageProcessor = messageProcessor;
        this.messageDecodingFunction = messageDecodingFunction;
        this.decodingErrorHandler = decodingErrorHandler;
    }

    public InboundListener(MessageProcessor<P> messageProcessor,
                           Function<E, M> messageDecodingFunction) {
        this.messageProcessor = messageProcessor;
        this.messageDecodingFunction = messageDecodingFunction;
        this.decodingErrorHandler = (error, message) -> {
          throw new PayloadProcessingException(error);
        };
    }
}
