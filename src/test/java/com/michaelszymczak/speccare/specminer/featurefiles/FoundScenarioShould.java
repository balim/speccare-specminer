package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class FoundScenarioShould {

    @Test public void storeReferenceToTheWrappingFeatureSoThatOnaCanFindTheFeaturesFile() {
        Feature feature = FeatureBuilder.use().build();
        Scenario scenario = new FoundScenario(validName(), validContent(), feature);
        Assert.assertSame(feature, scenario.getFeature());
    }

    @Test public void useFoundAsItsResult() {
        Scenario scenario = new FoundScenario(validName(), validContent(), validFeature());
        Assert.assertSame(ResultStatus.FOUND, scenario.getResult());
    }

    @Test public void provideWrappingFeaturePath() {
        Feature feature = FeatureBuilder.use().withPath("/some/path/foo.feature").build();
        Scenario scenario = new FoundScenario(validName(), validContent(), feature);
        Assert.assertSame("/some/path/foo.feature", scenario.getFeaturePath());
    }


    @Test public void provideOriginalContentOfTheScenarioSoThatItCanBeDisplayedInDocumentation() {
        List<String> lines = Arrays.asList(
                "Scenario: Foo title",
                "    Given bar"
        );
        Scenario scenario = new FoundScenario(validName(), lines, validFeature());
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