package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.ExistingScenario;
import com.michaelszymczak.speccare.specminer.core.Feature;
import com.michaelszymczak.speccare.specminer.core.InvalidScenarioContentException;
import com.michaelszymczak.speccare.specminer.core.Scenario;

import java.util.List;

public class ScenarioCreator {
    private final TextFragmentProvider tfp;

    public ScenarioCreator(TextFragmentProvider textFragmentProvider) {
        tfp = textFragmentProvider;
    }

    public Scenario create(List<String> content, Feature feature) {
        List<String> scenarioNames = tfp.getAllFragmentsThatFollows(content, new String[]{Scenario.SCENARIO_START, Scenario.SCENARIO_OUTLINE_START});
        if (scenarioNames.isEmpty()) {
            throw new InvalidScenarioContentException("No 'Scenario:' nor 'Scenario Outline:' line in scenario content: " + content.toString());
        }
        if (scenarioNames.size() > 1) {
            throw new InvalidScenarioContentException("Too many 'Scenario:' or 'Scenario Outline:' lines in scenario content: " + content.toString());
        }
        return new ExistingScenario(scenarioNames.get(0), content, feature);
    }
}
