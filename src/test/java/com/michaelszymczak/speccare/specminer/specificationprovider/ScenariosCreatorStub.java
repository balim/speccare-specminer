package com.michaelszymczak.speccare.specminer.specificationprovider;

import com.michaelszymczak.speccare.specminer.core.Scenario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ScenariosCreatorStub implements ScenariosCreatable {

    public final List<Scenario> scenarios = new ArrayList<>();

    @Override
    public List<Scenario> create() throws IOException {
        return scenarios;
    }
}
