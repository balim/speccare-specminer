package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.Feature;
import com.michaelszymczak.speccare.specminer.core.Scenario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Take a look at http://mvnrepository.com/artifact/info.cukes/gherkin/2.7.3 instead
class GherkinScenarioProvider implements ScenarioProvider {

    private final TextFragmentProvider tfp;
    private final FeaturesCreator featuresCreator;
    private final ScenarioCreator scenarioCreator;

    public GherkinScenarioProvider(TextFragmentProvider textFragmentProvider, FeaturesCreator featuresCreator) {
        this.tfp = textFragmentProvider;
        this.featuresCreator = featuresCreator;
        this.scenarioCreator = new ScenarioCreator(this.tfp);
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
        List<List<String>> scenariosContent = new ArrayList<>();
        List<String> currentScenarioLines = null;
        List<Scenario> scenarios = new ArrayList<>();
        boolean isInMultilineQuotation = false;

        for (String line : feature.getContent()) {
            if (tfp.isMultilineQuotation(line)) {
                isInMultilineQuotation = !isInMultilineQuotation;
            }
            if (isScenarioStartingLine(line) && !isInMultilineQuotation) {
                addNewScenario(scenarios, currentScenarioLines, feature);
                currentScenarioLines = createPlaceholderForNewScenarioLines(scenariosContent);
            }
            if (null != currentScenarioLines) {
                currentScenarioLines.add(line);
            }
        }

        addNewScenario(scenarios, currentScenarioLines, feature);
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

    private void addNewScenario(List<Scenario> scenarios, List<String> scenarioContent, Feature feature) {
        if (null != scenarioContent) {
            scenarios.add(scenarioCreator.create(scenarioContent, feature));
        }
    }
}
