package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.io.IOException;
import java.util.List;

interface ScenariosCreatable {
    public List<Scenario> create() throws IOException;
}
