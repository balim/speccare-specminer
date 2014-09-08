package com.michaelszymczak.speccare.specminer.core;


import java.util.Arrays;
import java.util.List;

public class FeatureBuilder {
    private String name = "Default feature";
    private String pathToFeatureFile = "/default/path/Feature.feature";
    private List<String> featureFileContent = Arrays.asList("Feature: Default feature", "  Scenario: Default scenario");

    public static FeatureBuilder use()
    {
        return new FeatureBuilder();
    }

    public FeatureBuilder withName(String name) {
        this.name = name;
        return this;
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
        return new Feature() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getPath() {
                return pathToFeatureFile;
            }

            @Override
            public List<String> getContent() {
                return featureFileContent;
            }
        };
    }


}
