package com.michaelszymczak.livingdocumentation.specificationprovider;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FeatureShould {

    @Test public void provideFeatureNameBasedOnTheContentPassedDuringCreation() {
        List<String> featureFileContent = Arrays.asList("Feature: Foo feature");

        Feature feature = createFeaturePassingContent(featureFileContent);

        Assert.assertEquals("Foo feature", feature.getName());
    }

    @Test public void provideScenarioNameIgnoringEmptyLinesAndTrailingSpaces() {
        List<String> featureFileContent = Arrays.asList("    Feature:    Bar feature    ");

        Feature feature = createFeaturePassingContent(featureFileContent);

        Assert.assertEquals("Bar feature", feature.getName());
    }

    @Test public void acceptEmptyFeatureName() {
        List<String> featureFileContent = Arrays.asList("Feature:");

        Feature feature = createFeaturePassingContent(featureFileContent);

        Assert.assertEquals("", feature.getName());
    }

    @Test(expected = InvalidFeatureContentException.class)
    public void throwExceptionIfNoFeatureLine() {
        List<String> featureFileContent = Arrays.asList("Given Foo without feature name");

        createFeaturePassingContent(featureFileContent);
    }

    @Test(expected = InvalidFeatureContentException.class)
    public void throwExceptionIfTooManyFeatureLines() {
        List<String> featureFileContent = Arrays.asList("Feature: foo", "Feature: bar");

        createFeaturePassingContent(featureFileContent);
    }







    private Feature createFeaturePassingContent(List<String> featureFileContent) {
        String pathToFeatureFile = "/foo/bar/featureFile.feature";
        List<Scenario> featureScenarios = new LinkedList<>();
        return new Feature(new TextFragmentProvider(), pathToFeatureFile, featureFileContent, featureScenarios);
    }


}