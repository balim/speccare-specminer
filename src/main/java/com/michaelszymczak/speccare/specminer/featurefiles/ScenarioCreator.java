package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.Feature;
import com.michaelszymczak.speccare.specminer.core.Scenario;

import java.util.ArrayList;
import java.util.List;

public class ScenarioCreator {
    private final TextFragmentProvider tfp;

    public ScenarioCreator(TextFragmentProvider textFragmentProvider) {
        tfp = textFragmentProvider;
    }

    public Scenario create(List<String> content, Feature feature) {
        return new FoundScenario(scenarioName(content), skipEmptyLines(content), feature);
    }

    private String scenarioName(List<String> content) {
        List<String> scenarioNames = tfp.getAllFragmentsThatFollows(content, new String[]{Scenario.SCENARIO_START, Scenario.SCENARIO_OUTLINE_START});
        if (scenarioNames.isEmpty()) {
            throw new InvalidScenarioContent("No 'Scenario:' nor 'Scenario Outline:' line in scenario content: " + content.toString());
        }
        if (scenarioNames.size() > 1) {
            throw new InvalidScenarioContent("Too many 'Scenario:' or 'Scenario Outline:' lines in scenario content: " + content.toString());
        }
        return scenarioNames.get(0);
    }

    private List<String> skipEmptyLines(List<String> content) {
        List<String> contentWithoutEmptyLines = new ArrayList<>();
        for (String line : content) {
            if (!line.trim().isEmpty()) {
                contentWithoutEmptyLines.add(line);
            }
        }
        return contentWithoutEmptyLines;
    }
}
