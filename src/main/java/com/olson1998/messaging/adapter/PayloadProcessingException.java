package com.olson1998.messaging.adapter;

public class PayloadProcessingException extends RuntimeException {

    private final Exception error;

    @Override
    public String getMessage() {
        return "Payload processing exception, reason: " + error.getMessage();
    }

    protected PayloadProcessingException(Exception error) {
        this.error = error;
    }

}
