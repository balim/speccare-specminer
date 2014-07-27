package com.michaelszymczak.livingdocumentation.cucumberSteps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class ScenarioJson {
    public static final String ABSOLUTE_PATH_TO_FEATURES_DIR_PLACEHOLDER = "ABSOLUTE_PATH_TO_FEATURES_DIR";
    private final JsonObject jsonObject;

    private ScenarioJson(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public static ScenarioJson createFromString(String jsonString) {
        return new ScenarioJson(createJsonObject(jsonString));
    }

    public static ScenarioJson createFromStringCorrectingFeaturesDir(String jsonString, String featuresDir) {
        JsonObject jsonObject = createJsonObject(jsonString);
        String realPathToFeatureFile = jsonObject.get("path").getAsString().replace(ABSOLUTE_PATH_TO_FEATURES_DIR_PLACEHOLDER, featuresDir);
        jsonObject.remove("path");
        jsonObject.addProperty("path", realPathToFeatureFile);
        return new ScenarioJson(jsonObject);
    }

    public String toString() {
        return jsonObject.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof ScenarioJson)) return false;
        return jsonObject.equals(((ScenarioJson) other).jsonObject);
    }

    private static JsonObject createJsonObject(String jsonString) {
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }
}
