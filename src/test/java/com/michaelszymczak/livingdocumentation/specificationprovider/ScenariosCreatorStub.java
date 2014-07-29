package com.michaelszymczak.livingdocumentation.specificationprovider;

import com.michaelszymczak.livingdocumentation.domain.Scenario;

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
