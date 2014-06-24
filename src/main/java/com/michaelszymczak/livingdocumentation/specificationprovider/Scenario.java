package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.util.ArrayList;
import java.util.List;

public class Scenario {

    private String name = null;

    public Scenario(List<String> scenarioContent) {
        name = extractName(scenarioContent);
    }

    public String getName() {
        return name;
    }


    private String extractName(List<String> scenarioContent) {
        ArrayList<String> scenarioNames = extractAllPotentialScenarioNames(scenarioContent);
        if (scenarioNames.size() == 0) {
            throw new InvalidScenarioContentException("No 'Scenario:' nor 'Scenario Outline:' string in scenario content: " + scenarioContent.toString());
        }
        if (scenarioNames.size() > 1) {
            throw new InvalidScenarioContentException("Too many 'Scenario:' or 'Scenario Outline:' strings in scenario content: " + scenarioContent.toString());
        }
        return scenarioNames.get(0);
    }

    private ArrayList<String> extractAllPotentialScenarioNames(List<String> scenarioContent) {
        ArrayList<String> scenarioNames = new ArrayList<>();
        for(String line : scenarioContent) {
            if (null != searchForScenarioNameInLine(line)) {
                scenarioNames.add(searchForScenarioNameInLine(line));
            }
        }

        return scenarioNames;
    }

    private String searchForScenarioNameInLine(String line) {
        line = line.trim();
        if (line.startsWith("Scenario:")) {
            return line.substring(9).trim();
        }
        if (line.startsWith("Scenario Outline:")) {
            return line.substring(17).trim();
        }
        return null;
    }
}
