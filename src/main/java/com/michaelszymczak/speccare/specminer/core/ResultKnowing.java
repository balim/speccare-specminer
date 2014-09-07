package com.michaelszymczak.speccare.specminer.core;


import java.io.IOException;
import java.io.Reader;

public interface ResultKnowing {
    public ResultStatus getResult(Reader sourceReader, String scenarioName) throws IOException;
    public ResultStatus getResult(String source, String scenarioName)  throws IOException;
}
