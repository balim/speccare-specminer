package com.michaelszymczak.speccare.specminer.featurefiles;


import com.michaelszymczak.speccare.specminer.core.*;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

public class AmbiguousScenarioShould {

    @Test public void presetItselfAsAmbiguousFoundScenario() {
        Assert.assertEquals("Too many scenarios matching searched phrase", getAmbiguousScenarioPointingToTwoFeatures().getName());
        Assert.assertEquals(ResultStatus.AMBIGUOUS, getAmbiguousScenarioPointingToTwoFeatures().getResult());
    }

    @Test public void notConveyAnyContent() {
        Assert.assertEquals(0, getAmbiguousScenarioPointingToTwoFeatures().getContent().size());
    }

    @Test public void presentAllFoundFeaturePathsSeparatedByComma() {
        AmbiguousScenario scenario = AmbiguousScenarioBuilder.use().addFeaturePath("/foopath/foo.feature").addFeaturePath("/barpath/bar.feature").build();
        Assert.assertEquals("/foopath/foo.feature,/barpath/bar.feature", scenario.getFeaturePath());
    }

    @Test public void haveNotFoundFeatureAsItsFeatureObject() {
        Assert.assertSame(NotFoundFeature.getInstance(), getAmbiguousScenarioPointingToTwoFeatures().getFeature());
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
        return ScenarioBuilder.use().withWrappingFeature(FeatureBuilder.use().withPath(foo).build()).build();
    }
}
