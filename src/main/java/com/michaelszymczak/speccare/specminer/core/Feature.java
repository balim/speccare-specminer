package com.michaelszymczak.speccare.specminer.core;

import java.util.List;

public abstract class Feature {
    public static final String FEATURE_START = "Feature:";

    private static final NotFoundFeature NOT_FOUND_FEATURE = new NotFoundFeature();
    public static NotFoundFeature getNotFound() {
        return NOT_FOUND_FEATURE;
    }

    public abstract String getName();
    public abstract String getPath();
    public abstract List<String> getContent();
}
