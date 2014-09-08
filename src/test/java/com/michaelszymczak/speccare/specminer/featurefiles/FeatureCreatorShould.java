package com.michaelszymczak.speccare.specminer.featurefiles;


import com.michaelszymczak.speccare.specminer.core.Feature;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FeatureCreatorShould {

    private FeatureCreator creator;
    @Before public void setUp() {
        creator = new FeatureCreator(new TextFragmentProvider());
    }

    @Test public void
    createFeatureWithNameBasedOnContentPassedDuringCreation() {
        List<String> content = Arrays.asList("Feature: Foo feature");
        Feature feature = creator.create(content, validPath());
        assertEquals("Foo feature", feature.getName());
    }

    @Test public void
    createPreservingContentAsIsSoThatItCanBeUsedLater() {
        List<String> content = Arrays.asList("Feature: Foo feature");
        Feature feature = creator.create(content, validPath());
        assertEquals("Feature: Foo feature", feature.getContent().get(0));
    }

    @Test public void
    preservePathToFeatureFileSoThatItCanBeEasilyFindOnDiskLater() {
        Feature feature = creator.create(validContent(), "/bar/foo.feature");
        assertEquals("/bar/foo.feature", feature.getPath());
    }

    @Test public void
    createScenarioNameIgnoringEmptyLinesAndTrailingSpaces() {
        List<String> content = Arrays.asList("    Feature:    Bar feature    ");
        Feature feature = creator.create(content, validPath());
        assertEquals("Bar feature", feature.getName());
    }

    @Test public void
    acceptEmptyFeatureName() {
        List<String> content = Arrays.asList("Feature:");
        Feature feature = creator.create(content, validPath());
        assertEquals("", feature.getName());
    }

    @Test(expected = InvalidFeatureContent.class) public void
    throwExceptionIfNoFeatureLine() {
        List<String> content = Arrays.asList("Given Foo without feature name");
        creator.create(content, validPath());
    }

    @Test(expected = InvalidFeatureContent.class) public void
    throwExceptionIfTooManyFeatureLines() {
        List<String> content = Arrays.asList("Feature: foo", "Feature: bar");
        creator.create(content, validPath());
    }

    @Test public void
    ignoreFeatureKeywordInsideInlineQuotation() {
        List<String> content = Arrays.asList(
                "Feature: My feature",
                "\"Feature: only a quote, not yet another feature\""
        );

        Feature feature = creator.create(content, validPath());

        assertEquals("\"Feature: only a quote, not yet another feature\"", feature.getContent().get(1));
    }

    @Test public void
    ignoreFeatureKeywordInsideMultilineQuotation() {
        List<String> content = Arrays.asList(
                "Feature: My feature",
                "  Scenario: quoting a feature",
                "    Given I have quotation as follows:",
                "\"\"\"",
                "  Feature: only a quote, not yet another feature",
                "\"\"\"",
                "    Then everything should be fine"
        );

        Feature feature = creator.create(content, validPath());

        assertEquals("  Feature: only a quote, not yet another feature", feature.getContent().get(4));
    }

    @Test(expected = InvalidFeatureContent.class) public void
    spotAProblemWhenFeatureKeywordOutsideQuotation() {
        List<String> content = Arrays.asList(
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

        creator.create(content, validPath());
    }

    private String validPath() {
        return "/foo/bar.feature";
    }

    private List<String> validContent() {
        return Arrays.asList("Feature: Foo feature");
    }
}
