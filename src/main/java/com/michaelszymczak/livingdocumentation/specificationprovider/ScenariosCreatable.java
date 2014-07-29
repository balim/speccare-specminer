package com.michaelszymczak.livingdocumentation.specificationprovider;

import com.michaelszymczak.livingdocumentation.domain.Scenario;

import java.io.IOException;
import java.util.List;

interface ScenariosCreatable {
    public List<Scenario> create() throws IOException;
}
