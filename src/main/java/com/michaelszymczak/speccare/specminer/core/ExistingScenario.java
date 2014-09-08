package com.michaelszymczak.speccare.specminer.core;


import java.util.Collections;
import java.util.List;

public class ExistingScenario extends Scenario {
    private String name;
    private final Feature feature;
    private final List<String> content;

    public ExistingScenario(String name, List<String> scenarioContent, Feature wrappingFeature) {
        feature = wrappingFeature;
        content = Collections.unmodifiableList(scenarioContent);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Feature getFeature() {
        return feature;
    }

    @Override
    public List<String> getContent() {
        return content;
    }

    @Override
    public ResultStatus getResult() {
        return ResultStatus.FOUND;
    }

}
