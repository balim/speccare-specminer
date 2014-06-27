package com.michaelszymczak.livingdocumentation.specificationprovider;


import org.junit.Assert;
import org.junit.Test;

public class NotFoundFeatureShould {

    @Test public void beCreatedUsingFactoryMethod() {
        Assert.assertSame(NotFoundFeature.class, Feature.getNotFound().getClass());
    }

    @Test public void beAlwaysTheSameInstanceAsItIsImmutableObject() {
        Assert.assertSame(Feature.getNotFound(), Feature.getNotFound());
    }

    @Test public void presetThemselfAsNotFoundScenario() {
        NotFoundFeature feature = Feature.getNotFound();

        Assert.assertEquals("Scenario not found", feature.getName());
        Assert.assertEquals(0, feature.getContent().size());
        Assert.assertEquals("", feature.getPath());
    }
}
