package com.michaelszymczak.livingdocumentation.specificationprovider;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ScenarioShould {

    @Test public void storeReferenceToTheWrappingFeatureSoThatOnaCanFindTheFeaturesFile() {
        ExistingFeature feature = createWrappingFeature();
        Scenario scenario = createScenarioPassingWrappingFeature(feature);
        Assert.assertSame(feature, scenario.getFeature());
    }

    @Test public void provideOriginalContentOfTheScenarioSoThatItCanBeDisplayedInDocumentation() {
        Scenario scenario = createScenarioFromContent(
                "Scenario: Foo title",
                "    Given bar"
        );

        Assert.assertEquals(Arrays.asList(
                "Scenario: Foo title",
                "    Given bar"
        ), scenario.getContent());
    }

    @Test public void provideScenarioNameBasedOnTheContentPassedDuringCreation() {
        Scenario scenario = createScenarioFromContent(
                "Scenario: Foo title",
                "    Given bar"
        );

        Assert.assertEquals("Foo title", scenario.getName());
    }

    @Test public void provideScenarioNameIgnoringEmptyLinesAndTrailingSpaces() {
        Scenario scenario = createScenarioFromContent(
                "    ",
                "  Scenario: Bar title    ",
                "    Given bar"
        );

        Assert.assertEquals("Bar title", scenario.getName());
    }

    @Test public void treatUseScenarioTemplateAsScenarioNameAsWell() {
        Scenario scenario = createScenarioFromContent(
                "  Scenario Outline: Foo scenario outline name",
                "    Given bar"
        );

        Assert.assertEquals("Foo scenario outline name", scenario.getName());
    }

    @Test public void acceptEmptyScenarioName() {
        Scenario scenario = createScenarioFromContent("Scenario:");

        Assert.assertEquals("", scenario.getName());
    }

    @Test(expected = InvalidScenarioContentException.class)
    public void throwExceptionIfNoScenarioLine() {
        createScenarioFromContent("Given foo");
    }

    @Test(expected = InvalidScenarioContentException.class)
    public void throwExceptionIfTooManyScenarioLines() {
        createScenarioFromContent(
            "Scenario: Foo",
            "Scenario: Bar"
        );
    }

    @Test(expected = InvalidScenarioContentException.class)
    public void throwExceptionIfBothScenarioAndScenarioOutlinePresent() {
        createScenarioFromContent(
            "Scenario: Foo",
            "Scenario Outline: Bar"
        );

    }

    private Scenario createScenarioFromContent(String... scenarioContent) {
        return ScenarioBuilder.use().withContent(scenarioContent).build();
    }

    private Scenario createScenarioPassingWrappingFeature(ExistingFeature wrappingFeature) {
        return ScenarioBuilder.use().withWrappingFeature(wrappingFeature).build();
    }

    private ExistingFeature createWrappingFeature() {
        return new ExistingFeature(new TextFragmentProvider(), "/path/to/Feature.feature", Arrays.asList("Feature: Foo"));
    }

}