package com.michaelszymczak.livingdocumentation.specificationprovider;

import com.michaelszymczak.livingdocumentation.domain.ExistingScenario;
import com.michaelszymczak.livingdocumentation.domain.Feature;
import com.michaelszymczak.livingdocumentation.domain.Scenario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ScenariosCreator implements ScenariosCreatable {

    private final TextFragmentProvider tfp;
    private final FeaturesCreator featuresCreator;

    public ScenariosCreator(TextFragmentProvider textFragmentProvider, FeaturesCreator featuresCreator) {
        this.tfp = textFragmentProvider;
        this.featuresCreator = featuresCreator;
    }

    public List<Scenario> create() throws IOException {
        List<Scenario> scenarios = new ArrayList<>();
        for (Feature feature : featuresCreator.create()) {
            for (Scenario scenario : createFromOneFeature(feature)) {
                scenarios.add(scenario);
            }
        }
        return scenarios;
    }

    public List<Scenario> createFromOneFeature(Feature feature) {

        SingleScenarioCreator ssc = new SingleScenarioCreator(tfp, feature);

        List<List<String>> scenariosContent = new ArrayList<>();
        List<String> currentScenarioLines = null;
        List<Scenario> scenarios = new ArrayList<>();

        for (String line : feature.getContent()) {
            if (isScenarioStartingLine(line)) {
                ssc.addNewScenario(scenarios, currentScenarioLines);
                currentScenarioLines = createPlaceholderForNewScenarioLines(scenariosContent);
            }
            if (null != currentScenarioLines) {
                currentScenarioLines.add(line);
            }
        }

        ssc.addNewScenario(scenarios, currentScenarioLines);
        return scenarios;
    }

    private List<String> createPlaceholderForNewScenarioLines(List<List<String>> scenariosContent) {
        List<String> currentScenarioLines;
        currentScenarioLines = new ArrayList<>();
        scenariosContent.add(currentScenarioLines);
        return currentScenarioLines;
    }

    private boolean isScenarioStartingLine(String line) {
        return tfp.returnStringFollowingAnyOf(line, new String[]{Scenario.SCENARIO_START, Scenario.SCENARIO_OUTLINE_START}) != null;
    }

    private class SingleScenarioCreator {
        private final TextFragmentProvider tfp;
        private final Feature feature;
        public SingleScenarioCreator(TextFragmentProvider tfp, Feature feature) {
            this.tfp = tfp;
            this.feature = feature;
        }

        public void addNewScenario(List<Scenario> scenarios, List<String> scenarioContent) {
            if (null != scenarioContent) {
                scenarios.add(create(scenarioContent));
            }
        }

        private Scenario create(List<String> scenarioContent) {
            return new ExistingScenario(tfp, scenarioContent, feature);
        }
    }
}
