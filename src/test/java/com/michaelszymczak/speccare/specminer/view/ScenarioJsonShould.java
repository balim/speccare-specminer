package com.michaelszymczak.speccare.specminer.view;

import com.michaelszymczak.speccare.specminer.core.NotFoundScenario;
import com.michaelszymczak.speccare.specminer.core.Scenario;
import com.michaelszymczak.speccare.specminer.specificationprovider.ExistingFeatureBuilder;
import com.michaelszymczak.speccare.specminer.specificationprovider.ExistingScenarioBuilder;
import org.junit.Assert;
import org.junit.Test;

public class ScenarioJsonShould {
    @Test public void
    getJsonBasedOnThePassedScenario() {
        Scenario scenario = ExistingScenarioBuilder.use().withWrappingFeature(
                ExistingFeatureBuilder.use().withPath("/path/to/Foo.feature").build()
        ).withContent("Scenario: Bar title", "    Given baz").build();

        ScenarioJson scenarioJson = new ScenarioJson(scenario);
        Assert.assertEquals(
                "{\"name\":\"Bar title\",\"path\":\"/path/to/Foo.feature\",\"content\":[\"Scenario: Bar title\",\"    Given baz\"],\"result\":\"found\"}",
                scenarioJson.toString()
        );
    }

    @Test public void
    presentAnyOtherTypeOfScenario() {
        Scenario notFoundScenario = new NotFoundScenario();
        ScenarioJson scenarioJson = new ScenarioJson(notFoundScenario);
        Assert.assertEquals(
                "{\"name\":\"Scenario not found\",\"path\":\"\",\"content\":[],\"result\":\"notfound\"}",
                scenarioJson.toString()
        );
    }
}
