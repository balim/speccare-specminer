package com.michaelszymczak.speccare.specminer.domain;


import java.io.IOException;

public interface Determinable {
    public ResultStatus getResult(String scenarioName) throws IOException;
}
