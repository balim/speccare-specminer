package com.michaelszymczak.speccare.specminer.cucumber;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.michaelszymczak.speccare.specminer.core.ResultAggregate;
import com.michaelszymczak.speccare.specminer.core.ResultStatus;

class EntryJsonObject {
    private JsonObject object;

    public EntryJsonObject(JsonValue value) {
        this.object = value.asObject();
    }

    public ResultStatus getResult(String scenarioName) {
        ResultAggregate aggregate = new ResultAggregate();
        for (JsonValue value : object.get("elements").asArray()) {
            aggregate.add(new ScenarioJsonObject(value).getResult(scenarioName));
        }
        return aggregate.result();
    }

}
