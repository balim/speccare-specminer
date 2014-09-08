package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.Feature;

import java.util.Collections;
import java.util.List;

class NotFoundFeature extends Feature {
    private static final NotFoundFeature NOT_FOUND_FEATURE = new NotFoundFeature();

    public static NotFoundFeature getInstance() {
        return NOT_FOUND_FEATURE;
    }

    private NotFoundFeature() {
    }

    @Override
    public String getName() {
        return "Feature not found";
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
