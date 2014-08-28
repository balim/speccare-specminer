package com.michaelszymczak.speccare.specminer.view;

import com.google.gson.Gson;
import com.michaelszymczak.speccare.specminer.domain.Scenario;

import java.util.List;

public class ScenarioJson {
    // used to create Json
    @SuppressWarnings("unused") private final String name;
    @SuppressWarnings("unused") private final String path;
    @SuppressWarnings("unused") private final List<String> content;
    @SuppressWarnings("unused") private final String result;

    // according to https://sites.google.com/site/gson :
    // The Gson instance does not maintain any state while invoking Json operations.
    // So, you are free to reuse the same object for multiple Json serialization and deserialization operations.
    private static final Gson GSON = new Gson();

    public ScenarioJson(Scenario scenario) {
        name = scenario.getName();
        path = scenario.getFeaturePath();
        content = scenario.getContent();
        result = scenario.getResult().toString();
    }

    public String toString() {
        return GSON.toJson(this);
    }

}
