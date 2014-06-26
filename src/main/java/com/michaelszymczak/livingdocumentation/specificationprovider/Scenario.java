package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.util.ArrayList;
import java.util.List;

class Scenario {

    public static final String SCENARIO_START = "Scenario:";
    public static final String SCENARIO_OUTLINE_START = "Scenario Outline:";
    private final String name;
    private final TextFragmentProvider tfp;
    private final Feature feature;
    private final List<String> content;

    public Scenario(TextFragmentProvider textFragmentProvider, List<String> scenarioContent, Feature wrappingFeature) {
        tfp = textFragmentProvider;
        feature = wrappingFeature;
        content = scenarioContent;
        name = extractName(scenarioContent);
    }

    public String getName() {
        return name;
    }

    public Feature getFeature() {
        return feature;
    }

    private String extractName(List<String> scenarioContent) {
        ArrayList<String> scenarioNames = tfp.getAllFragmentsThatFollows(scenarioContent, new String[]{SCENARIO_START, SCENARIO_OUTLINE_START});
        if (scenarioNames.size() == 0) {
            throw new InvalidScenarioContentException("No 'Scenario:' nor 'Scenario Outline:' line in scenario content: " + scenarioContent.toString());
        }
        if (scenarioNames.size() > 1) {
            throw new InvalidScenarioContentException("Too many 'Scenario:' or 'Scenario Outline:' lines in scenario content: " + scenarioContent.toString());
        }
        return scenarioNames.get(0);
    }

    public List<String> getContent() {
        return content;
    }
}
