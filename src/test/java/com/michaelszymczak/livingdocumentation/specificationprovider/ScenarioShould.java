package com.michaelszymczak.livingdocumentation.specificationprovider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ScenarioShould {

    @Test public void provideScenarioNameBasedOnTheContentPassedDuringCreation() {
        List<String> scenarioContent = Arrays.asList(new String[] {
           "Scenario: Foo title",
           "    Given bar"
        });

        Scenario scenario = new Scenario(scenarioContent);

        Assert.assertEquals("Foo title", scenario.getName());
    }

    @Test public void provideScenarioNameIgnoringEmptyLinesAndTrailingSpaces() {
        List<String> scenarioContent = Arrays.asList(new String[] {
                "    ",
                "  Scenario: Bar title    ",
                "    Given bar"
        });

        Scenario scenario = new Scenario(scenarioContent);

        Assert.assertEquals("Bar title", scenario.getName());
    }

    @Test public void treatUseScenarioTemplateAsScenarioNameAsWell() {
        List<String> scenarioContent = Arrays.asList(new String[] {
                "  Scenario Outline: Foo scenario outline name",
                "    Given bar"
        });

        Scenario scenario = new Scenario(scenarioContent);

        Assert.assertEquals("Foo scenario outline name", scenario.getName());
    }


    @Test(expected = InvalidScenarioContentException.class)
    public void throwExceptionIfNoScenarioLine() {
        List<String> scenarioContent = Arrays.asList(new String[] {
                "Given foo",
        });

        new Scenario(scenarioContent);
    }

    @Test(expected = InvalidScenarioContentException.class)
    public void throwExceptionIfTooManyScenarioLines() {
        List<String> scenarioContent = Arrays.asList(new String[] {
                "Scenario: Foo",
                "Scenario: Bar",
        });

        new Scenario(scenarioContent);
    }

    @Test(expected = InvalidScenarioContentException.class)
    public void throwExceptionIfBothScenarioAndScenarioOutlinePresent() {
        List<String> scenarioContent = Arrays.asList(new String[] {
                "Scenario: Foo",
                "Scenario Outline: Bar",
        });

        new Scenario(scenarioContent);
    }

    @Before public void setUp() {
    }

}