package com.michaelszymczak.speccare.specminer.domain;

import java.util.Collections;
import java.util.List;

public class AmbiguousScenario extends Scenario {
    private final List<Scenario> foundScenarios;
    public AmbiguousScenario(List<Scenario> foundScenarios) {
        if (foundScenarios.size() < 2) {
            throw new IllegalArgumentException("At least two found scenarios make for ambiguity");
        }
        this.foundScenarios = foundScenarios;
    }

    @Override
    public String getName() {
        return "Too many scenarios matching searched phrase";
    }

    @Override
    public Feature getFeature() {
        return Feature.getNotFound();
    }

    @Override
    public List<String> getContent() {
        return Collections.emptyList();
    }

    @Override
    public String getResult() {
        return "toomany";
    }

    @Override
    public String getFeaturePath() {
        return getWithoutEndingComma(getFeaturePathsSeparatedByComma());
    }

    private String getFeaturePathsSeparatedByComma() {
        StringBuilder message = new StringBuilder();
        for(Scenario scenario : foundScenarios) {
            message.append(scenario.getFeaturePath());
            message.append(",");
        }
        return message.toString();
    }

    private String getWithoutEndingComma(String result) {
        return result.substring(0, result.length()-1);
    }
}
