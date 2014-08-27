package com.michaelszymczak.speccare.specminer.domain;

public enum ResultStatus {
    FAILED("failed"),
    NOT_FOUND("notfound"),
    IGNORED("ignored"),
    SKIPPED("skipped"),
    AMBIGUOUS("ambiguous"),
    UNKNOWN("unknown"),
    PASSED("passed"),
    FOUND("found");

    private final String canonicalName;

    ResultStatus(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    @Override
    public String toString() {
        return canonicalName;
    }
}
