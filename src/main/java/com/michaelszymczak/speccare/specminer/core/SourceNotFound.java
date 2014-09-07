package com.michaelszymczak.speccare.specminer.core;

public class SourceNotFound extends RuntimeException {

    public SourceNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
