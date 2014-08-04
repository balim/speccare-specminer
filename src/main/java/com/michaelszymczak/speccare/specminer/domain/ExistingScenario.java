package com.michaelszymczak.speccare.specminer.domain;


import com.michaelszymczak.speccare.specminer.specificationprovider.TextFragmentProvider;
import com.michaelszymczak.speccare.specminer.view.ScenarioJson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExistingScenario extends Scenario {
    private final String name;
    private final TextFragmentProvider tfp;
    private final Feature feature;
    private final List<String> content;
    private final ScenarioJson scenarioJson;

    public ExistingScenario(TextFragmentProvider textFragmentProvider, List<String> scenarioContent, Feature wrappingFeature) {
        tfp = textFragmentProvider;
        feature = wrappingFeature;
        content = Collections.unmodifiableList(scenarioContent);
        name = extractName(content);
        scenarioJson = new ScenarioJson(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Feature getFeature() {
        return feature;
    }

    @Override
    public List<String> getContent() {
        return content;
    }

    @Override
    public String getResult() {
        return "found";
    }

    @Override
    public String toJson() {
        return scenarioJson.toString();
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
}
