package com.michaelszymczak.livingdocumentation.domain;

public class InvalidScenarioContentException extends RuntimeException {
    public InvalidScenarioContentException(String message) {
        super(message);
    }
}
