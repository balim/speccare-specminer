package com.michaelszymczak.speccare.specminer.jsonobject;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.michaelszymczak.speccare.specminer.domain.ResultAggregate;
import com.michaelszymczak.speccare.specminer.domain.ResultStatus;

public class EntryJsonObject {
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
