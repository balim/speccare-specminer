package com.michaelszymczak.speccare.specminer.domain;

public class SourceNotFound extends RuntimeException {

    public SourceNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
