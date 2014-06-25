package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScenariosCreator {

    private TextFragmentProvider tfp;

    public ScenariosCreator(TextFragmentProvider textFragmentProvider) {
        tfp = textFragmentProvider;
    }

    public List<Scenario> create(Feature feature) {
        List<String> scenarioContent = new ArrayList<>();
        boolean isScenarioStarted = false;
        for (String line : feature.getContent()) {
            if (isCurrentLineCandidateForScenarioContent(isScenarioStarted, line)) {
                isScenarioStarted = true;
                scenarioContent.add(line);
            }
        }

        return Arrays.asList(new Scenario(tfp, scenarioContent, feature));
    }

    private boolean isCurrentLineCandidateForScenarioContent(boolean scenarioStarted, String line) {
        return scenarioStarted || tfp.returnStringFollowingAnyOf(line, new String[]{"Scenario:", "Scenario Outline:"}) != null;
    }
}
