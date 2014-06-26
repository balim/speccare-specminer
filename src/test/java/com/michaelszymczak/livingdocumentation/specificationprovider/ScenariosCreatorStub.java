package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScenariosCreatorStub implements ScenariosCreatable {

    public List<Scenario> scenarios = new ArrayList<>();

    @Override
    public List<Scenario> create() throws IOException {
        return scenarios;
    }
}
