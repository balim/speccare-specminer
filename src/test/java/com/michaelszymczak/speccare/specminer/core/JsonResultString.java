package com.michaelszymczak.speccare.specminer.core;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class JsonResultString {

    private final String name;
    private final ResultStatus result;
    private final List<ResultStatus> results;
    private final String type;

    public static class Builder {
        private String name = "Scenario Foo";
        private ResultStatus result = ResultStatus.UNKNOWN;
        private List<ResultStatus> results = new ArrayList<>();
        private String type = "scenario";

        public Builder name(String val) {
            name = val; return this;
        }

        public Builder result(ResultStatus val) {
            results = new ArrayList<>();
            results.add(val);
            result = val;
            return this;
        }

        public Builder noResult() {
            results = new ArrayList<>();
            result = null;
            return this;
        }

        public Builder nextResult(ResultStatus val) {
            results.add(val); return this;
        }

        public Builder type(String val) {
            type = val; return this;
        }

        public Reader asReader() {
            return new JsonResultString(this).toReader();
        }

        public String asString() {
            return new JsonResultString(this).toString();
        }

        public String asOneEntryString() {
            return new JsonResultString(this).toOneEntryString();
        }

        public String asOneScenarioString() {
            return new JsonResultString(this).toOneScenarioString();
        }
    }

    private JsonResultString(Builder builder) {
        name = builder.name;
        result = builder.result;
        results = builder.results;
        type = builder.type;
    }


    @Override
    public String toString() {
        return "[" + toOneEntryString() + "]";
    }

    private String toOneEntryString() {
        return "{\"elements\": [" + toOneScenarioString() + "]}";
    }

    private String toOneScenarioString() {
        return "{\"name\": \"" + name + "\",\"steps\": [" + resultPart() + "],\"type\": \"" + type + "\"}";
    }

    private String resultPart() {
        StringBuilder stringBuilder = new StringBuilder();
        String separator = "";
        for (ResultStatus result : results) {
            stringBuilder.append(separator);
            stringBuilder.append("{\"result\": {\"status\": \"" + result + "\"}}");
            separator = ",";
        }
        return stringBuilder.toString();
    }

    public Reader toReader() {
        return new StringReader(toString());
    }

}
