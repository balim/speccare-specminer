package com.michaelszymczak.speccare.specminer.domain;


import com.michaelszymczak.speccare.specminer.specificationprovider.TextFragmentProvider;

import java.util.Collections;
import java.util.List;

public class ExistingScenario extends Scenario {
    private final String name;
    private final TextFragmentProvider tfp;
    private final Feature feature;
    private final List<String> content;

    public ExistingScenario(TextFragmentProvider textFragmentProvider, List<String> scenarioContent, Feature wrappingFeature) {
        tfp = textFragmentProvider;
        feature = wrappingFeature;
        content = Collections.unmodifiableList(scenarioContent);
        name = extractName(content);
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
    public ResultStatus getResult() {
        return ResultStatus.FOUND;
    }

    private String extractName(List<String> scenarioContent) {
        List<String> scenarioNames = tfp.getAllFragmentsThatFollows(scenarioContent, new String[]{SCENARIO_START, SCENARIO_OUTLINE_START});
        if (scenarioNames.isEmpty()) {
            throw new InvalidScenarioContentException("No 'Scenario:' nor 'Scenario Outline:' line in scenario content: " + scenarioContent.toString());
        }
        if (scenarioNames.size() > 1) {
            throw new InvalidScenarioContentException("Too many 'Scenario:' or 'Scenario Outline:' lines in scenario content: " + scenarioContent.toString());
        }
        return scenarioNames.get(0);
    }
}
