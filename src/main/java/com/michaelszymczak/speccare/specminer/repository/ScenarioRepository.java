package com.michaelszymczak.speccare.specminer.repository;


import com.michaelszymczak.speccare.specminer.core.Scenario;

import java.io.IOException;
import java.util.List;

public interface ScenarioRepository {
    public List<Scenario> findByNameFragment(String fragment) throws IOException;
}
