package com.michaelszymczak.speccare.specminer.cucumber;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.michaelszymczak.speccare.specminer.core.ResultStatus;

import static com.michaelszymczak.speccare.specminer.core.ResultStatus.*;

class ScenarioJsonObject {

    private final JsonObject scenario;

    public ScenarioJsonObject(JsonValue value) {
        this.scenario = value.asObject();
    }

    public ResultStatus getResult(String scenarioName) {
        if (scenario.get("name").asString().equals(scenarioName) && "scenario".equals(scenario.get("type").asString())) {
            return result();
        } else {
            return NOT_FOUND;
        }
    }

    private ResultStatus result() {
        JsonArray steps = steps(scenario);
        if (steps.isEmpty()) {
            return UNKNOWN;
        }
        for(JsonValue step : steps) {
            ResultStatus result = stepResult(step);
            if (result != PASSED) {
                return result;
            }
        }
        return PASSED;
    }

    private JsonArray steps(JsonObject foundScenario) {
        return foundScenario.get("steps").asArray();
    }

    private ResultStatus stepResult(JsonValue value) {
        JsonObject step = value.asObject();
        String status = step.get("result").asObject().get("status").asString();
        return ofFallback(status);
    }
}
