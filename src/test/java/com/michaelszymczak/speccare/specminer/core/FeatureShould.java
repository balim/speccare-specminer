package com.michaelszymczak.speccare.specminer.core;


import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class FeatureShould {
    @Test public void
    haveEmptyFeature() {
        Feature feature = Feature.getEmpty();

        assertTrue(feature.getContent().isEmpty());
        assertTrue(feature.getName().isEmpty());
        assertTrue(feature.getPath().isEmpty());
    }

    @Test public void
    haveOneInstanceOfEmptyFeatureAsASingleNullObject() {
        assertSame(Feature.getEmpty(), Feature.getEmpty());
    }


}
