package com.michaelszymczak.livingdocumentation.domain;

import java.util.List;

public abstract class Scenario {

    public static final String SCENARIO_START = "Scenario:";
    public static final String SCENARIO_OUTLINE_START = "Scenario Outline:";

    private final static NotFoundScenario notFoundScenario = new NotFoundScenario();
    public static NotFoundScenario getNotFound() {
        return notFoundScenario;
    }

    public abstract String getName();
    public abstract Feature getFeature();
    public abstract List<String> getContent();
    public abstract String toJson();
}
