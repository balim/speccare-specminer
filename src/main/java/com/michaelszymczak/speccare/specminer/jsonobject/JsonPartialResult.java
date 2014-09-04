package com.michaelszymczak.speccare.specminer.jsonobject;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;
import com.michaelszymczak.speccare.specminer.domain.Determinable;
import com.michaelszymczak.speccare.specminer.domain.ResultAggregate;
import com.michaelszymczak.speccare.specminer.domain.ResultStatus;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;


public class JsonPartialResult implements Determinable {

    private final JsonArray jsonArray;

    public JsonPartialResult(String json) throws IOException {
        this(new StringReader(json));
    }

    public JsonPartialResult(Reader json) throws IOException {
        jsonArray = JsonArray.readFrom(json);
    }

    @Override
    public ResultStatus getResult(String scenarioName) {
        ResultAggregate aggregate = new ResultAggregate();
        for (JsonValue each : jsonArray.asArray()) {
            aggregate.add(new EntryJsonObject(each).getResult(scenarioName));
        }
        return aggregate.result();
    }
}
