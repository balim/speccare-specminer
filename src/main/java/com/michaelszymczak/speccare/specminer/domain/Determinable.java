package com.michaelszymczak.speccare.specminer.domain;


import java.io.IOException;
import java.io.Reader;

public interface Determinable {
    public ResultStatus getResult(Reader sourceReader, String scenarioName) throws IOException;
    public ResultStatus getResult(String source, String scenarioName)  throws IOException;
}
