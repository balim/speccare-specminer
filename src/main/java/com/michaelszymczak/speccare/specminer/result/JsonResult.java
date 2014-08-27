package com.michaelszymczak.speccare.specminer.result;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


public class JsonResult {

    public static enum Result {
        FAILED, NOT_FOUND, IGNORED, SKIPPED, AMBIGUOUS, UNKNOWN, PASSED
    }

    private final JsonArray jsonArray;

    public JsonResult(String json) throws IOException {
        this(new StringReader(json));
    }

    public JsonResult(Reader json) throws IOException {
        jsonArray = JsonArray.readFrom(json);
    }

    public Result getResult(String scenarioName) {
        List<JsonObject> found = findScenarios(scenarioName);
        if (found.isEmpty()) {
            return Result.NOT_FOUND;
        }
        if (found.size() > 1) {
            return Result.AMBIGUOUS;
        }
        return getScenarioResult(found.get(0));
    }

    private List<JsonObject> findScenarios(String scenarioName) {
        List<JsonObject> found = new ArrayList<>();
        for (JsonValue value : jsonArray.get(0).asObject().get("elements").asArray()) {
            JsonObject scenario = value.asObject();
            if (scenario.get("name").asString().equals(scenarioName) && "scenario".equals(scenario.get("type").asString())) {
                found.add(scenario);
            }
        }
        return found;
    }

    private Result getScenarioResult(JsonObject foundScenario) {
        for(JsonValue value : foundScenario.get("steps").asArray()) {
            JsonObject step = value.asObject();
            String status = step.get("result").asObject().get("status").asString();
            Result result = resultFromStatus(status);
            if (result != Result.PASSED) {
                return result;
            }
        }
        return Result.PASSED;
    }

    private Result resultFromStatus(String status) {
        if ("failed".equals(status)) {
            return Result.FAILED;
        }
        if ("ignored".equals(status)) {
            return Result.IGNORED;
        }
        if ("skipped".equals(status)) {
            return Result.SKIPPED;
        }
        if ("passed".equals(status)) {
            return Result.PASSED;
        }
        return Result.UNKNOWN;
    }
}
