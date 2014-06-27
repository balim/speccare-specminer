package com.michaelszymczak.livingdocumentation.specificationprovider;


import java.util.Arrays;
import java.util.List;

public class FeatureBuilder {
    private TextFragmentProvider tfp = new TextFragmentProvider();
    private String pathToFeatureFile = "/default/path/Feature.feature";
    private List<String> featureFileContent = Arrays.asList("Feature: Default feature");

    public static FeatureBuilder use()
    {
        return new FeatureBuilder();
    }

    public FeatureBuilder withTextFragmentProvider(TextFragmentProvider tfp) {
        this.tfp = tfp;
        return this;
    }

    public FeatureBuilder withPath(String pathToFeatureFile) {
        this.pathToFeatureFile = pathToFeatureFile;
        return this;
    }

    public FeatureBuilder withContent(List<String> featureFileContent) {
        this.featureFileContent = featureFileContent;
        return this;
    }

    public FeatureBuilder withContent(String... featureFileContent) {
        this.featureFileContent = Arrays.asList(featureFileContent);
        return this;
    }

    public ExistingFeature build() {
        return new ExistingFeature(tfp, pathToFeatureFile, featureFileContent);
    }
}
