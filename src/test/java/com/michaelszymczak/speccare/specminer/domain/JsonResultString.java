package com.michaelszymczak.speccare.specminer.domain;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonResultString {
//    "[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}},{\"result\": {\"status\": \"ignored\"}}],\"type\": \"scenario\"}]}]"

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
            result = val; return this;
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
    }

    private JsonResultString(Builder builder) {
        name = builder.name;
        result = builder.result;
        results = builder.results;
        type = builder.type;
    }

    @Override
    public String toString() {
        StringBuilder resultPart = new StringBuilder();
        String separator = "";
        for (ResultStatus result : results) {
            resultPart.append(separator);
            resultPart.append("{\"result\": {\"status\": \"" + result + "\"}}");
            separator = ",";
        }
        return "[{\"elements\": [{\"name\": \"" + name + "\",\"steps\": [" + resultPart.toString() + "],\"type\": \"" + type + "\"}]}]";
    }

    public Reader toReader() {
        return new StringReader(toString());
    }

}
