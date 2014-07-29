package com.michaelszymczak.livingdocumentation.domain;

public class InvalidFeatureContentException extends RuntimeException {
    public InvalidFeatureContentException(String message) {
        super(message);
    }
}
