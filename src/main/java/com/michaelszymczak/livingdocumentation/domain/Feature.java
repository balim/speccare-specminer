package com.michaelszymczak.livingdocumentation.domain;

import java.util.List;

public abstract class Feature {
    public static final String FEATURE_START = "Feature:";

    private final static NotFoundFeature notFoundFeature = new NotFoundFeature();
    public static NotFoundFeature getNotFound() {
        return notFoundFeature;
    }

    public abstract String getName();
    public abstract String getPath();
    public abstract List<String> getContent();
}
