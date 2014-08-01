package com.michaelszymczak.speccare.specminer.domain;


import org.junit.Assert;
import org.junit.Test;

public class NotFoundFeatureShould {

    @Test public void beCreatedUsingFactoryMethod() {
        Assert.assertSame(NotFoundFeature.class, Feature.getNotFound().getClass());
    }

    @Test public void beAlwaysTheSameInstanceAsItIsImmutableObject() {
        Assert.assertSame(Feature.getNotFound(), Feature.getNotFound());
    }

    @Test public void presetItselfAsNotFoundFeature() {
        NotFoundFeature feature = Feature.getNotFound();

        Assert.assertEquals("Feature not found", feature.getName());
        Assert.assertEquals(0, feature.getContent().size());
        Assert.assertEquals("", feature.getPath());
    }
}
