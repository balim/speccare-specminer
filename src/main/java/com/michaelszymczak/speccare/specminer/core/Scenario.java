package com.michaelszymczak.speccare.specminer.core;

import java.util.List;

public abstract class Scenario {

    public static final String SCENARIO_START = "Scenario:";
    public static final String SCENARIO_OUTLINE_START = "Scenario Outline:";
    private static final NotFoundScenario NOT_FOUND_SCENARIO = new NotFoundScenario();

    public static NotFoundScenario getNotFound() {
        return NOT_FOUND_SCENARIO;
    }

    public static Scenario copyWithNewResult(Scenario origin, ResultStatus newResult) {
        return new BasicScenario(origin.getName(), origin.getFeature(), origin.getContent(), newResult);
    }

    public String getFeaturePath() {
        return getFeature().getPath();
    }

    public abstract String getName();
    public abstract Feature getFeature();
    public abstract List<String> getContent();
    public abstract ResultStatus getResult();

    private static class BasicScenario extends Scenario {

        private final String name;
        private final Feature feature;
        private final List<String> content;

        private final ResultStatus result;

        private BasicScenario(String name, Feature feature, List<String> content, ResultStatus result) {
            this.name = name;
            this.feature = feature;
            this.content = content;
            this.result = result;
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
            return result;
        }
    }

}
