package com.michaelszymczak.speccare.specminer.core;


import com.michaelszymczak.speccare.specminer.featurefiles.TextFragmentProvider;

import java.util.Arrays;
import java.util.List;

public class FeatureBuilder {
    private final TextFragmentProvider tfp = new TextFragmentProvider();
    private String pathToFeatureFile = "/default/path/Feature.feature";
    private List<String> featureFileContent = Arrays.asList("Feature: Default feature", "  Scenario: Default scenario");

    public static FeatureBuilder use()
    {
        return new FeatureBuilder();
    }

    public FeatureBuilder withPath(String pathToFeatureFile) {
        this.pathToFeatureFile = pathToFeatureFile;
        return this;
    }

    public FeatureBuilder withContent(String... featureFileContent) {
        this.featureFileContent = Arrays.asList(featureFileContent);
        return this;
    }

    public Feature build() {
        return new ExistingFeature(tfp, pathToFeatureFile, featureFileContent);
    }


}
