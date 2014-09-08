package com.michaelszymczak.speccare.specminer.core;

import java.util.List;

public class Scenario {

    public static final String SCENARIO_START = "Scenario:";
    public static final String SCENARIO_OUTLINE_START = "Scenario Outline:";

    private final String name;
    private final Feature feature;
    private final List<String> content;
    private final ResultStatus result;

    public static Scenario copyWithNewResult(Scenario origin, ResultStatus newResult) {
        return new Scenario(origin.getName(), origin.getFeature(), origin.getContent(), newResult);
    }

    protected Scenario(String name, Feature feature, List<String> content, ResultStatus result) {
        this.name = name;
        this.feature = feature;
        this.content = content;
        this.result = result;
    }

    public String getName() {
        return name;
    }
    public Feature getFeature() {
        return feature;
    }
    public List<String> getContent() {
        return content;
    }
    public ResultStatus getResult() {
        return result;
    }
    public String getFeaturePath() {
        return getFeature().getPath();
    }

}
