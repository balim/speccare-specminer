package com.michaelszymczak.speccare.specminer.domain;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

class JsonObjectScenario {

    private final JsonObject foundScenario;

    public JsonObjectScenario(JsonObject foundScenario) {
        this.foundScenario = foundScenario;
    }

    public ResultStatus getResult() {
        JsonArray steps = steps(foundScenario);
        if (steps.isEmpty()) {
            return ResultStatus.UNKNOWN;
        }
        for(JsonValue step : steps) {
            ResultStatus result = stepResult(step);
            if (result != ResultStatus.PASSED) {
                return result;
            }
        }
        return ResultStatus.PASSED;
    }

    private JsonArray steps(JsonObject foundScenario) {
        return foundScenario.get("steps").asArray();
    }

    private ResultStatus stepResult(JsonValue value) {
        JsonObject step = value.asObject();
        String status = step.get("result").asObject().get("status").asString();
        return resultFromStatus(status);
    }

    private ResultStatus resultFromStatus(String status) {
        if ("failed".equals(status)) {
            return ResultStatus.FAILED;
        }
        if ("ignored".equals(status)) {
            return ResultStatus.IGNORED;
        }
        if ("skipped".equals(status)) {
            return ResultStatus.SKIPPED;
        }
        if ("passed".equals(status)) {
            return ResultStatus.PASSED;
        }
        return ResultStatus.UNKNOWN;
    }
}
