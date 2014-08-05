package com.michaelszymczak.speccare.specminer.specificationprovider;

import com.michaelszymczak.speccare.specminer.domain.Scenario;

import java.io.IOException;
import java.util.List;

interface ScenariosCreatable {
    List<Scenario> create() throws IOException;
}
