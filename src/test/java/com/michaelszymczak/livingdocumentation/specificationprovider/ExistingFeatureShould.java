package com.michaelszymczak.livingdocumentation.specificationprovider;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ExistingFeatureShould {

    @Test public void provideFeatureNameBasedOnTheContentPassedDuringCreation() {
        ExistingFeature feature = ExistingFeatureBuilder.use().withContent("Feature: Foo feature").build();
        Assert.assertEquals("Foo feature", feature.getName());
    }

    @Test public void provideScenarioNameIgnoringEmptyLinesAndTrailingSpaces() {
        ExistingFeature feature = ExistingFeatureBuilder.use().withContent("    Feature:    Bar feature    ").build();
        Assert.assertEquals("Bar feature", feature.getName());
    }

    @Test public void acceptEmptyFeatureName() {
        ExistingFeature feature = ExistingFeatureBuilder.use().withContent("Feature:").build();
        Assert.assertEquals("", feature.getName());
    }

    @Test public void storePathToFeatureFileSoThatItCanBeEasilyFindOnDiskLater() {
        ExistingFeature feature = ExistingFeatureBuilder.use().withPath("/path/to/myFeature.feature").build();
        Assert.assertEquals("/path/to/myFeature.feature", feature.getPath());
    }

    @Test public void storeContentOfTheFeatureFileAsIsSoThatWeKnowTheExactContentOfTheOriginalFile() {
        ExistingFeature feature = ExistingFeatureBuilder.use().withContent("Feature: My feature", "  Next line").build();
        Assert.assertEquals(Arrays.asList("Feature: My feature", "  Next line"), feature.getContent());
    }

    @Test(expected = InvalidFeatureContentException.class)
    public void throwExceptionIfNoFeatureLine() {
        ExistingFeatureBuilder.use().withContent("Given Foo without feature name").build();
    }

    @Test(expected = InvalidFeatureContentException.class)
    public void throwExceptionIfTooManyFeatureLines() {
        ExistingFeatureBuilder.use().withContent("Feature: foo", "Feature: bar").build();
    }
}