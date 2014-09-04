package com.michaelszymczak.speccare.specminer.domain;

import com.eclipsesource.json.JsonArray;
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
        ResultAggregate aggregate = new ResultAggregate();
        for (JsonValue each : jsonArray.asArray()) {
            aggregate.add(new JsonObjectEntry(each.asObject()).getResult(scenarioName));
        }
        return aggregate.result();
    }

    private static class ResultAggregate {

        private final List<ResultStatus> results = new ArrayList<>();

        public void add(ResultStatus result) {
            if (ResultStatus.NOT_FOUND != result) {
                results.add(result);
            }
        }

        public ResultStatus result() {
            if (results.isEmpty()) {
                return ResultStatus.NOT_FOUND;
            }
            if (results.size() > 1) {
                return ResultStatus.AMBIGUOUS;
            }

            return results.get(0);
        }

    }
}
