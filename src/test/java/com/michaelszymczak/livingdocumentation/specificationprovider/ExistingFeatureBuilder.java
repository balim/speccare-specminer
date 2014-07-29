package com.michaelszymczak.livingdocumentation.specificationprovider;


import com.michaelszymczak.livingdocumentation.domain.ExistingFeature;

import java.util.Arrays;
import java.util.List;

public class ExistingFeatureBuilder {
    private final TextFragmentProvider tfp = new TextFragmentProvider();
    private String pathToFeatureFile = "/default/path/Feature.feature";
    private List<String> featureFileContent = Arrays.asList("Feature: Default feature", "  Scenario: Default scenario");

    public static ExistingFeatureBuilder use()
    {
        return new ExistingFeatureBuilder();
    }

    public ExistingFeatureBuilder withPath(String pathToFeatureFile) {
        this.pathToFeatureFile = pathToFeatureFile;
        return this;
    }

    public ExistingFeatureBuilder withContent(String... featureFileContent) {
        this.featureFileContent = Arrays.asList(featureFileContent);
        return this;
    }

    public ExistingFeature build() {
        return new ExistingFeature(tfp, pathToFeatureFile, featureFileContent);
    }
}
