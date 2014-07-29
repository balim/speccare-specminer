package com.michaelszymczak.livingdocumentation.domain;


import com.google.gson.Gson;
import com.michaelszymczak.livingdocumentation.specificationprovider.TextFragmentProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExistingScenario extends Scenario {
    private final String name;
    private final TextFragmentProvider tfp;
    private final Feature feature;
    private final List<String> content;

    // according to https://sites.google.com/site/gson :
    // The Gson instance does not maintain any state while invoking Json operations.
    // So, you are free to reuse the same object for multiple Json serialization and deserialization operations.
    private final static Gson gson = new Gson();

    public ExistingScenario(TextFragmentProvider textFragmentProvider, List<String> scenarioContent, Feature wrappingFeature) {
        tfp = textFragmentProvider;
        feature = wrappingFeature;
        content = Collections.unmodifiableList(scenarioContent);
        name = extractName(content);
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

    @Override
    public String toJson() {
        return gson.toJson(new JsonFields(this.name, this.feature.getPath(), this.content));
    }

    private static class JsonFields {
        private final String name;
        private final String path;
        private final List<String> content;
        private final String result = "found";

        public JsonFields(String name, String path, List<String> content) {
            this.name = name;
            this.path = path;
            this.content = content;

        }
    }
}
