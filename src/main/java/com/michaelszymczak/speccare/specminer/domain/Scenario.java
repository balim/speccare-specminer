package com.michaelszymczak.speccare.specminer.domain;

import java.util.List;

public abstract class Scenario {

    public static final String SCENARIO_START = "Scenario:";
    public static final String SCENARIO_OUTLINE_START = "Scenario Outline:";

    private static final NotFoundScenario NOT_FOUND_SCENARIO = new NotFoundScenario();
    public static NotFoundScenario getNotFound() {
        return NOT_FOUND_SCENARIO;
    }

    public abstract String getName();
    public abstract Feature getFeature();
    public abstract List<String> getContent();
    public abstract ResultStatus getResult();

    public String getFeaturePath() {
        return getFeature().getPath();
    }
}
