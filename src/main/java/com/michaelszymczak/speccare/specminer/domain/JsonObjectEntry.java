package com.michaelszymczak.speccare.specminer.domain;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

class JsonObjectEntry {
    private JsonObject entry;

    public JsonObjectEntry(JsonObject entry) {
        this.entry = entry;
    }

    public ResultStatus getResult(String scenarioName) {
        List<ResultStatus> found = new ArrayList<>();
        for (JsonValue value : entry.get("elements").asArray()) {
            JsonObject scenario = value.asObject();
            if (scenario.get("name").asString().equals(scenarioName) && "scenario".equals(scenario.get("type").asString())) {
                found.add(new JsonObjectScenario(scenario).getResult());
            }
        }
        return resultStatus(found);
    }

    private ResultStatus resultStatus(List<ResultStatus> found) {
        if (found.isEmpty()) {
            return ResultStatus.NOT_FOUND;
        }
        if (found.size() > 1) {
            return ResultStatus.AMBIGUOUS;
        }
        return found.get(0);
    }

}
