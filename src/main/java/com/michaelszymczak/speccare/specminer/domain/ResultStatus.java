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

    public static ResultStatus of(String name) {
        for (ResultStatus each : ResultStatus.values()) {
            if (each.toString().equals(name)) {
                return each;
            }
        }
        throw new IllegalArgumentException("No such ResultStatus name: " + name);
    }

    public static ResultStatus ofFallback(String name) {
        try {
            return of(name);
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
