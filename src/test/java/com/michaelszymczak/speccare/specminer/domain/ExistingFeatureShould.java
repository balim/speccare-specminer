package com.michaelszymczak.speccare.specminer.domain;

import com.michaelszymczak.speccare.specminer.specificationprovider.ExistingFeatureBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ExistingFeatureShould {

    @Test public void
    provideFeatureNameBasedOnTheContentPassedDuringCreation() {
        ExistingFeature feature = ExistingFeatureBuilder.use().withContent("Feature: Foo feature").build();
        Assert.assertEquals("Foo feature", feature.getName());
    }

    @Test public void
    provideScenarioNameIgnoringEmptyLinesAndTrailingSpaces() {
        ExistingFeature feature = ExistingFeatureBuilder.use().withContent("    Feature:    Bar feature    ").build();
        Assert.assertEquals("Bar feature", feature.getName());
    }

    @Test public void
    acceptEmptyFeatureName() {
        ExistingFeature feature = ExistingFeatureBuilder.use().withContent("Feature:").build();
        Assert.assertEquals("", feature.getName());
    }

    @Test public void
    storePathToFeatureFileSoThatItCanBeEasilyFindOnDiskLater() {
        ExistingFeature feature = ExistingFeatureBuilder.use().withPath("/path/to/myFeature.feature").build();
        Assert.assertEquals("/path/to/myFeature.feature", feature.getPath());
    }

    @Test(expected = InvalidFeatureContentException.class) public void
    throwExceptionIfNoFeatureLine() {
        ExistingFeatureBuilder.use().withContent("Given Foo without feature name").build();
    }

    @Test(expected = InvalidFeatureContentException.class) public void
    throwExceptionIfTooManyFeatureLines() {
        ExistingFeatureBuilder.use().withContent("Feature: foo", "Feature: bar").build();
    }

    @Test public void
    storeContentOfTheFeatureFileAsIsSoThatWeKnowTheExactContentOfTheOriginalFile() {
        assertContentOfTheFeaturePreserved(
                "Feature: My feature",
                "  Next line"
        );
    }

    @Test public void
    ignoreFeatureKeywordInsideInlineQuotation() {
        assertContentOfTheFeaturePreserved(
                "Feature: My feature",
                "\"Feature: only a quote, not yet another feature\""
        );
    }

    @Test public void
    ignoreFeatureKeywordInsideMultilineQuotation() {
        assertContentOfTheFeaturePreserved(
                "Feature: My feature",
                "  Scenario: quoting a feature",
                "    Given I have quotation as follows:",
                "\"\"\"",
                "  Feature: only a quote, not yet another feature",
                "\"\"\"",
                "    Then everything should be fine"
        );
    }

    @Test(expected = InvalidFeatureContentException.class) public void
    spotAProblemWhenFeatureKeywordOutsideQuotation() {
        assertContentOfTheFeaturePreserved(
                "Feature: My feature",
                "  Scenario: quoting a feature",
                "    Given I have quotation as follows:",
                "\"\"\"",
                "  Feature: only a quote, not yet another feature",
                "\"\"\"",
                "    Then everything should be fine",
                "Feature: but this one should not be here, outside quotation",
                "  Scenario: another scenario"
        );
    }



    private void assertContentOfTheFeaturePreserved(String... lines) {
        ExistingFeature feature = ExistingFeatureBuilder.use().withContent(lines).build();
        Assert.assertEquals(Arrays.asList(lines), feature.getContent());
    }

}