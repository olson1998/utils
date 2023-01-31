package com.olson1998.messaging.adapter;

import com.olson1998.messaging.domain.model.EncodedMessage;
import com.olson1998.messaging.domain.model.Inbound;
import com.olson1998.messaging.domain.model.Message;
import com.olson1998.messaging.domain.service.MessageProcessor;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class InboundListener<E extends EncodedMessage, M extends Message<P>, I extends Inbound<P>, P> {

    private final MessageProcessor<I, P> messageProcessor;
    private final Function<E, M> messageDecodingFunction;

    private final BiFunction<Map<String, String>, M, I> inboundFactory;

    private final BiFunction<Exception, E, PayloadProcessingException> payloadDeserializingErrorHandler;


    public void receive(Map<String, String> headers, E message){
        var decoded = decodeMessage(message);
        var inbound = inboundFactory.apply(headers, decoded);
        messageProcessor.process(inbound);
    }

    private M decodeMessage(E message){
        try{
            return messageDecodingFunction.apply(message);
        }catch (Exception error){
            if(payloadDeserializingErrorHandler != null){
                throw payloadDeserializingErrorHandler.apply(error, message);
            }else {
                throw new PayloadProcessingException(error);
            }
        }
    }

    public InboundListener(MessageProcessor<I, P> messageProcessor,
                           Function<E, M> messageDecodingFunction,
                           BiFunction<Map<String, String>, M, I> inboundFactory,
                           BiFunction<Exception, E, PayloadProcessingException> payloadDeserializingErrorHandler) {
        this.messageProcessor = messageProcessor;
        this.messageDecodingFunction = messageDecodingFunction;
        this.inboundFactory = inboundFactory;
        this.payloadDeserializingErrorHandler = payloadDeserializingErrorHandler;
    }

    public InboundListener(MessageProcessor<I, P> messageProcessor,
                           Function<E, M> messageDecodingFunction,
                           BiFunction<Map<String, String>, M, I> inboundFactory) {
        this.messageProcessor = messageProcessor;
        this.messageDecodingFunction = messageDecodingFunction;
        this.inboundFactory = inboundFactory;
        this.payloadDeserializingErrorHandler = (error, message) -> new PayloadProcessingException(error);
    }
}
