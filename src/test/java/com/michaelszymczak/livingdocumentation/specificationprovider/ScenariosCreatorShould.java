package com.michaelszymczak.livingdocumentation.specificationprovider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ScenariosCreatorShould {

    private ScenariosCreator sc;

    @Test public void createScenariosUsingFeatureContent() {
        Feature feature = createFeatureWithContent(Arrays.asList(
                "Feature: Foo feature",
                "",
                "  Scenario: Bar scenario",
                "    Given some precondition",
                "    Then  some expected result"
        ));

        List<Scenario> scenarios = sc.create(feature);

        Assert.assertEquals(Arrays.asList(
                "  Scenario: Bar scenario",
                "    Given some precondition",
                "    Then  some expected result"
        ), scenarios.get(0).getContent());
    }

    @Test public void usePassedFeatureAsScenariosWrappingFeature() {
        Feature feature = createFeature();
        List<Scenario> scenarios = sc.create(feature);
        Assert.assertSame(feature, scenarios.get(0).getFeature());
    }

    private Feature createFeatureWithContent(List<String> content) {
        return new Feature(new TextFragmentProvider(), "path/to/Feature.feature", content);
    }

    private Feature createFeature() {
        return createFeatureWithContent(Arrays.asList(
                "Feature: Foo feature",
                "",
                "  Scenario: Bar scenario",
                "    Given some precondition",
                "    Then  some expected result"
        ));
    }

    @Test public void useProvidedFeatureAsTheWrappingFeature() {

    }

    @Before
    public void setUp() throws Exception {
        sc = new ScenariosCreator(new TextFragmentProvider());
    }
}