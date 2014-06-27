package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.util.ArrayList;
import java.util.List;

public class NotFoundScenario extends Scenario {
    @Override
    public String getName() {
        return "Scenario not found";
    }

    @Override
    public Feature getFeature() {
        return Feature.getNotFound();
    }

    @Override
    public List<String> getContent() {
        return new ArrayList<>();
    }

    @Override
    public String toJson() {
        return "{\"name\":\"Scenario not found\",\"path\":\"\",\"content\":[],\"result\":\"notfound\"}";
    }
}
