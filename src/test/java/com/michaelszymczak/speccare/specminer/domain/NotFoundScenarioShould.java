package com.michaelszymczak.speccare.specminer.domain;


import org.junit.Assert;
import org.junit.Test;

public class NotFoundScenarioShould {

    @Test public void beCreatedUsingFactoryMethod() {
        Assert.assertSame(NotFoundScenario.class, Scenario.getNotFound().getClass());
    }

    @Test public void beAlwaysTheSameInstanceAsItIsImmutableObject() {
        Assert.assertSame(Scenario.getNotFound(), Scenario.getNotFound());
    }

    @Test public void presetItselfAsNotFoundScenario() {
        NotFoundScenario scenario = Scenario.getNotFound();

        Assert.assertEquals("Scenario not found", scenario.getName());
        Assert.assertEquals(0, scenario.getContent().size());
    }

    @Test public void haveNotFoundFeatureAsItsFeatureObject() {
        NotFoundScenario scenario = Scenario.getNotFound();
        Assert.assertSame(Feature.getNotFound(), scenario.getFeature());
    }

    @Test public void returnInformationInJsonFormat() {
        NotFoundScenario scenario = Scenario.getNotFound();

        Assert.assertEquals(
                "{\"name\":\"Scenario not found\",\"path\":\"\",\"content\":[],\"result\":\"notfound\"}",
                scenario.toJson());
    }
}
