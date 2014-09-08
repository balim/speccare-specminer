package com.michaelszymczak.speccare.specminer.core;

import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

public class ExistingScenarioShould {

    @Test public void storeReferenceToTheWrappingFeatureSoThatOnaCanFindTheFeaturesFile() {
        ExistingFeature feature = ExistingFeatureBuilder.use().build();
        Scenario scenario = new ExistingScenario(validName(), validContent(), feature);
        Assert.assertSame(feature, scenario.getFeature());
    }

    @Test public void useFoundAsItsResult() {
        Scenario scenario = new ExistingScenario(validName(), validContent(), validFeature());
        Assert.assertSame(ResultStatus.FOUND, scenario.getResult());
    }

    @Test public void provideWrappingFeaturePath() {
        ExistingFeature feature = ExistingFeatureBuilder.use().withPath("/some/path/foo.feature").build();
        Scenario scenario = new ExistingScenario(validName(), validContent(), feature);
        Assert.assertSame("/some/path/foo.feature", scenario.getFeaturePath());
    }


    @Test public void provideOriginalContentOfTheScenarioSoThatItCanBeDisplayedInDocumentation() {
        List<String> lines = Arrays.asList(
                "Scenario: Foo title",
                "    Given bar"
        );
        Scenario scenario = new ExistingScenario(validName(), lines, validFeature());
        Assert.assertEquals(lines, scenario.getContent());
    }


    private NotFoundFeature validFeature() {
        return Feature.getNotFound();
    }

    private String validName() {
        return "Scenario name";
    }

    private List<String> validContent() {
        return Arrays.asList("Scenario: Scenario name");
    }
}