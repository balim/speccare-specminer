package com.michaelszymczak.speccare.specminer.core;

import java.util.Collections;
import java.util.List;

public abstract class Feature {
    public static final String FEATURE_START = "Feature:";
    public static Feature getEmpty() {
        return EMPTY_FEATURE;
    }

    public abstract String getName();
    public abstract String getPath();
    public abstract List<String> getContent();

    private static final Feature EMPTY_FEATURE = new EmptyFeature();
    private static class EmptyFeature extends Feature {
        @Override
        public String getName() {
            return "";
        }

        @Override
        public String getPath() {
            return "";
        }

        @Override
        public List<String> getContent() {
            return Collections.emptyList();
        }
    }
}
