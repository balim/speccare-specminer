package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.Feature;

import java.util.List;

public class FeatureCreator {
    private final TextFragmentProvider tfp;

    public FeatureCreator(TextFragmentProvider textFragmentProvider) {
        tfp = textFragmentProvider;
    }

    public Feature create(List<String> content, String pathToFeatureFile) {
        List<String> names = tfp.getAllFragmentsThatFollows(content, new String[]{Feature.FEATURE_START});
        if (names.isEmpty()) {
            throw new InvalidFeatureContent("No 'Feature:' line in feature content: " + content.toString());
        }
        if (names.size() > 1) {
            throw new InvalidFeatureContent("Too many 'Feature:' lines in feature content: " + content.toString());
        }
        return new ExistingFeature(names.get(0), pathToFeatureFile, content);
    }
}
