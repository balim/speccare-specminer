package com.michaelszymczak.speccare.specminer.domain;

import com.google.gson.Gson;

import java.util.List;

public abstract class Scenario {

    public static final String SCENARIO_START = "Scenario:";
    public static final String SCENARIO_OUTLINE_START = "Scenario Outline:";

    private final static NotFoundScenario NOT_FOUND_SCENARIO = new NotFoundScenario();
    public static NotFoundScenario getNotFound() {
        return NOT_FOUND_SCENARIO;
    }

    public abstract String getName();
    public abstract Feature getFeature();
    public abstract List<String> getContent();
    public abstract String getResult();
    public abstract String toJson();

    public String getFeaturePath() {
        return getFeature().getPath();
    }
}
