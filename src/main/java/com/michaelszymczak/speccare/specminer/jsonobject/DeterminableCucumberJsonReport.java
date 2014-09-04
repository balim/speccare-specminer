package com.michaelszymczak.speccare.specminer.jsonobject;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;
import com.michaelszymczak.speccare.specminer.domain.Determinable;
import com.michaelszymczak.speccare.specminer.domain.ResultAggregate;
import com.michaelszymczak.speccare.specminer.domain.ResultStatus;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;


public class DeterminableCucumberJsonReport implements Determinable {

    public ResultStatus getResult(String json, String scenarioName)  throws IOException {
        return getResult(new StringReader(json), scenarioName);
    }

    public ResultStatus getResult(Reader reader, String scenarioName) throws IOException {
        ResultAggregate aggregate = new ResultAggregate();
        for (JsonValue each : JsonArray.readFrom(reader).asArray()) {
            aggregate.add(new EntryJsonObject(each).getResult(scenarioName));
        }
        return aggregate.result();
    }
}
