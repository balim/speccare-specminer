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

    @Test public void storePathToFeatureFileSoThatItCanBeEasilyFindOnDiskLater() {
        Feature feature = createFeaturePassingPathToFeatureFile("/path/to/myFeature.feature");
        Assert.assertEquals("/path/to/myFeature.feature", feature.getPath());
    }

    @Test public void storeScenariosSoThatThenCanBeRetrievedLater() {
        List<Scenario> myScenarios = createSampleScenarios();
        Feature feature = createFeaturePassingScenarios(myScenarios);
        Assert.assertSame(myScenarios, feature.getScenarios());
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



    private Feature createFeaturePassingScenarios(List<Scenario> featureScenarios) {
        return createFeature(featureScenarios, "/foo/bar/featureFile.feature", Arrays.asList("Feature: foo"));
    }

    private Feature createFeaturePassingPathToFeatureFile(String pathToFeatureFile) {
        return createFeature(createSampleScenarios(), pathToFeatureFile, Arrays.asList("Feature: foo"));
    }

    private Feature createFeaturePassingContent(List<String> featureFileContent) {
        return createFeature(createSampleScenarios(), "/foo/bar/featureFile.feature", featureFileContent);
    }

    private Feature createFeature(List<Scenario> featureScenarios, String pathToFeatureFile, List<String> featureFileContent) {
        return new Feature(new TextFragmentProvider(), pathToFeatureFile, featureFileContent, featureScenarios);
    }

    private List<Scenario> createSampleScenarios() {
        return new LinkedList<>();
    }


}