package com.olson1998.messaging.adapter;

import com.olson1998.messaging.domain.model.EncodedMessage;
import com.olson1998.messaging.domain.model.Message;
import com.olson1998.messaging.domain.service.InboundFactory;
import com.olson1998.messaging.domain.service.MessageProcessor;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class InboundListener<E extends EncodedMessage, M extends Message<P>, P> {

    private final MessageProcessor messageProcessor;

    private final InboundFactory inboundFactory;

    private final Function<E, M> messageDecodingFunction;

    private final BiFunction<E, Exception, RuntimeException> decodingErrorHandler;

    public void receive(String topic, String tenantKey, long sendingTime, E message){
        var decoded = decodeMessage(message);
        var inbound = inboundFactory.fabricate(topic, tenantKey, sendingTime, System.currentTimeMillis(), decoded);
        messageProcessor.process(inbound);
    }

    private M decodeMessage(E message){
        try{
            return messageDecodingFunction.apply(message);
        }catch (Exception error){
            throw decodingErrorHandler.apply(message, error);
        }
    }

    public InboundListener(MessageProcessor messageProcessor,
                           InboundFactory inboundFactory,
                           Function<E, M> messageDecodingFunction,
                           BiFunction<E, Exception, RuntimeException> decodingErrorHandler) {
        this.messageProcessor = messageProcessor;
        this.inboundFactory = inboundFactory;
        this.messageDecodingFunction = messageDecodingFunction;
        this.decodingErrorHandler = decodingErrorHandler;
    }

    public InboundListener(MessageProcessor messageProcessor,
                           InboundFactory inboundFactory,
                           Function<E, M> messageDecodingFunction) {
        this.messageProcessor = messageProcessor;
        this.inboundFactory = inboundFactory;
        this.messageDecodingFunction = messageDecodingFunction;
        this.decodingErrorHandler = (encoded, error) -> new PayloadProcessingException(error);
    }
}
