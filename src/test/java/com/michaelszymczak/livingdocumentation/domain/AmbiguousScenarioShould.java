package com.michaelszymczak.livingdocumentation.domain;


import com.michaelszymczak.livingdocumentation.specificationprovider.AmbiguousScenarioBuilder;
import com.michaelszymczak.livingdocumentation.specificationprovider.ExistingFeatureBuilder;
import com.michaelszymczak.livingdocumentation.specificationprovider.ExistingScenarioBuilder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

public class AmbiguousScenarioShould {

    @Test public void returnJsonWithAllFeaturePathsWhereScenarioWasFound() {
        List<Scenario> foundScenarios = Arrays.asList(scenarioWithFeaturePath("foo.feature"), scenarioWithFeaturePath("bar.feature"));
        AmbiguousScenario ambiguousScenario = new AmbiguousScenario(foundScenarios);
        Assert.assertEquals("{\"result\":\"toomany\",\"name\":\"Too many scenarios matching searched phrase\",\"path\":\"foo.feature,bar.feature\",\"content\":[]}", ambiguousScenario.toJson());
    }

    @Test public void presetItselfAsAmbiguousFoundScenario() {
        Assert.assertEquals("Too many scenarios matching searched phrase", getAmbiguousScenarioPointingToTwoFeatures().getName());
    }

    @Test public void notConveyAnyContent() {
        Assert.assertEquals(0, getAmbiguousScenarioPointingToTwoFeatures().getContent().size());
    }

    @Test public void haveNotFoundFeatureAsItsFeatureObject() {
        Assert.assertSame(Feature.getNotFound(), getAmbiguousScenarioPointingToTwoFeatures().getFeature());
    }

    @Test public void throwExceptionIfLessThanTwoScenariosUsedToCreate() {
        List<Scenario> foundScenarios = Arrays.asList(scenarioWithFeaturePath("foo.feature"));
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("At least two found scenarios make for ambiguity");
        new AmbiguousScenario(foundScenarios);
    }

    @Rule public ExpectedException exception = ExpectedException.none();

    private AmbiguousScenario getAmbiguousScenarioPointingToTwoFeatures() {
        return AmbiguousScenarioBuilder.use().addFeaturePath("baz.feature").addFeaturePath("bar.feature").build();
    }


    private Scenario scenarioWithFeaturePath(String foo) {
        return ExistingScenarioBuilder.use().withWrappingFeature(ExistingFeatureBuilder.use().withPath(foo).build()).build();
    }
}
