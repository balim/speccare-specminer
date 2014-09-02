package com.michaelszymczak.speccare.specminer.domain;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


public class JsonPartialResult implements PartialResult {

    private final JsonArray jsonArray;

    public JsonPartialResult(String json) throws IOException {
        this(new StringReader(json));
    }

    public JsonPartialResult(Reader json) throws IOException {
        jsonArray = JsonArray.readFrom(json);
    }

    @Override
    public ResultStatus getResult(String scenarioName) {
        List<JsonObject> found = findScenarios(scenarioName);
        if (found.isEmpty()) {
            return ResultStatus.NOT_FOUND;
        }
        if (found.size() > 1) {
            return ResultStatus.AMBIGUOUS;
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

    private ResultStatus getScenarioResult(JsonObject foundScenario) {
        for(JsonValue value : foundScenario.get("steps").asArray()) {
            JsonObject step = value.asObject();
            String status = step.get("result").asObject().get("status").asString();
            ResultStatus result = resultFromStatus(status);
            if (result != ResultStatus.PASSED) {
                return result;
            }
        }
        return ResultStatus.PASSED;
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
