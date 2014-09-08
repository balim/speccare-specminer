package com.michaelszymczak.speccare.specminer.featurefiles;


import com.michaelszymczak.speccare.specminer.core.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ScenariosCreatorShould {

    @Test
    public void storeReferenceToTheWrappingFeatureSoThatOnaCanFindTheFeaturesFile() {
        Feature feature = FeatureBuilder.use().build();
        Scenario scenario = scenarioCreator.create(validContent(), feature);
        Assert.assertSame(feature, scenario.getFeature());
    }

    @Test
    public void contentAsIsSoThatCanBeUsedToDisplayAndProcessLater() {
        Scenario scenario = givenCreatedPassingContent("Scenario: Foo");
        Assert.assertEquals(Arrays.asList("Scenario: Foo"), scenario.getContent());
    }

    @Test public void useFoundAsItsResult() {
        Scenario scenario = scenarioCreator.create(validContent(), validFeature());
        Assert.assertSame(ResultStatus.FOUND, scenario.getResult());
    }

    @Test public void provideScenarioNameBasedOnTheContentPassedDuringCreation() {
        Scenario scenario = givenCreatedPassingContent(
                "Scenario: Foo title",
                "    Given bar"
        );

        Assert.assertEquals("Foo title", scenario.getName());
    }

    @Test public void provideScenarioNameIgnoringEmptyLinesAndTrailingSpaces() {
        Scenario scenario = givenCreatedPassingContent(
                "    ",
                "  Scenario: Bar title    ",
                "    Given bar"
        );

        Assert.assertEquals("Bar title", scenario.getName());
    }

    @Test public void treatScenarioTemplateAsAScenarioNameAsWell() {
        Scenario scenario = givenCreatedPassingContent(
                "  Scenario Outline: Foo scenario outline name",
                "    Given bar"
        );

        Assert.assertEquals("Foo scenario outline name", scenario.getName());
    }

    @Test public void acceptEmptyScenarioName() {
        Scenario scenario = givenCreatedPassingContent("Scenario: ");
        Assert.assertEquals("", scenario.getName());
    }


    @Test(expected = InvalidScenarioContentException.class)
    public void throwExceptionIfNoScenarioLine() {
        tryToCreateUsingContent("Given foo");
    }

    @Test(expected = InvalidScenarioContentException.class)
    public void throwExceptionIfTooManyScenarioLines() {
        tryToCreateUsingContent(
                "Scenario: Foo",
                "Scenario: Bar"
        );
    }

    @Test(expected = InvalidScenarioContentException.class)
    public void throwExceptionIfBothScenarioAndScenarioOutlinePresent() {
        tryToCreateUsingContent(
                "Scenario: Foo",
                "Scenario Outline: Bar"
        );
    }

    @Test public void
    ignoreScenarioKeywordInsideMultilineQuotation() {
        tryToCreateUsingContent(
                "Scenario: Foo",
                "  Given I have quotation as follows:",
                "  \"\"\"",
                "    Scenario: only a quote, not yet another scenario",
                "    Scenario Outline: only a quote, not yet another scenario",
                "\"\"\"",
                "  Then everything should be fine"
        );
    }

    @Test(expected = InvalidScenarioContentException.class) public void
    spotAProblemWhenFeatureKeywordOutsideQuotation() {
        tryToCreateUsingContent(
                "Scenario: too many scenario keywords",
                "  Given I have quotation as follows:",
                "  \"\"\"",
                "    Scenario: only a quote, not yet another scenario",
                "  \"\"\"",
                "    Then everything should be fine",
                "Scenario: but this one should not be here, outside quotation",
                "  Given foo"
        );
    }


    private ScenarioCreator scenarioCreator;

    @Before
    public void setUp() {
        scenarioCreator = new ScenarioCreator(new TextFragmentProvider());
    }

    private List<String> validContent() {
        return Arrays.asList("Scenario: foo");
    }

    private Feature validFeature() {
        return FeatureBuilder.use().build();
    }

    private void tryToCreateUsingContent(String... lines) {
        scenarioCreator.create(Arrays.asList(lines), validFeature());
    }

    private Scenario givenCreatedPassingContent(String... lines) {
        return scenarioCreator.create(Arrays.asList(lines), validFeature());
    }

}
