package com.michaelszymczak.speccare.specminer.featurefiles;


import com.michaelszymczak.speccare.specminer.core.Feature;
import com.michaelszymczak.speccare.specminer.core.ResultStatus;
import org.junit.Assert;
import org.junit.Test;

public class NotFoundScenarioShould {

    @Test public void beCreatedUsingFactoryMethod() {
        Assert.assertSame(NotFoundScenario.class, NotFoundScenario.getInstance().getClass());
    }

    @Test public void beAlwaysTheSameInstanceAsItIsImmutableObject() {
        Assert.assertSame(NotFoundScenario.getInstance(), NotFoundScenario.getInstance());
    }

    @Test public void presentItselfAsNotFoundScenario() {
        NotFoundScenario scenario = NotFoundScenario.getInstance();

        Assert.assertEquals("Scenario not found", scenario.getName());
        Assert.assertEquals(ResultStatus.NOT_FOUND, scenario.getResult());
        Assert.assertEquals(0, scenario.getContent().size());
    }

    @Test public void haveNotFoundFeatureAsItsFeatureObject() {
        NotFoundScenario scenario = NotFoundScenario.getInstance();
        Assert.assertSame(Feature.getNotFound(), scenario.getFeature());
    }
}
