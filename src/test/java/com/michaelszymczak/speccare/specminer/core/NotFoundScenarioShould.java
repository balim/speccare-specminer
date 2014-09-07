package com.michaelszymczak.speccare.specminer.core;


import org.junit.Assert;
import org.junit.Test;

public class NotFoundScenarioShould {

    @Test public void beCreatedUsingFactoryMethod() {
        Assert.assertSame(NotFoundScenario.class, Scenario.getNotFound().getClass());
    }

    @Test public void beAlwaysTheSameInstanceAsItIsImmutableObject() {
        Assert.assertSame(Scenario.getNotFound(), Scenario.getNotFound());
    }

    @Test public void presentItselfAsNotFoundScenario() {
        NotFoundScenario scenario = Scenario.getNotFound();

        Assert.assertEquals("Scenario not found", scenario.getName());
        Assert.assertEquals(ResultStatus.NOT_FOUND, scenario.getResult());
        Assert.assertEquals(0, scenario.getContent().size());
    }

    @Test public void haveNotFoundFeatureAsItsFeatureObject() {
        NotFoundScenario scenario = Scenario.getNotFound();
        Assert.assertSame(Feature.getNotFound(), scenario.getFeature());
    }
}
