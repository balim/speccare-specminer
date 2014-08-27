package com.michaelszymczak.speccare.specminer.domain;


public abstract class Result {
    public abstract ResultStatus getResult(String scenarioName);
}
