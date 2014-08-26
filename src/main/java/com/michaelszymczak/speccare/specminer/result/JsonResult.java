package com.michaelszymczak.speccare.specminer.result;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;


public class JsonResult {
    public static enum result {FAILED, NOT_FOUND, IGNORED, SKIPPED, PASSED}

    private final JsonArray jsonArray;

    public JsonResult(String jsonString) throws IOException {
        this(new StringReader(jsonString));
    }

    private JsonResult(Reader jsonReader) throws IOException {
        jsonArray = JsonArray.readFrom(jsonReader);
    }

    public result getResult(String scenarioName) {
        JsonObject foundScenario = findScenario(scenarioName);
        if (null == foundScenario) {
            return result.NOT_FOUND;
        }
        return getScenarioResult(foundScenario);
    }

    private JsonObject findScenario(String scenarioName) {
        JsonObject foundScenario = null;
        for (JsonValue value : jsonArray.get(0).asObject().get("elements").asArray()) {
            JsonObject scenario = value.asObject();
            if (scenario.get("name").asString().equals(scenarioName)) {
                foundScenario = scenario;
                break;
            }
        }
        return foundScenario;
    }

    private result getScenarioResult(JsonObject foundScenario) {
        for(JsonValue value : foundScenario.get("steps").asArray()) {
            JsonObject step = value.asObject();
            String status = step.get("result").asObject().get("status").asString();
            if (status.equals("failed")) {
                return result.FAILED;
            }
            if (status.equals("ignored")) {
                return result.IGNORED;
            }
            if (status.equals("skipped")) {
                return result.SKIPPED;
            }
        }
        return result.PASSED;
    }
}
