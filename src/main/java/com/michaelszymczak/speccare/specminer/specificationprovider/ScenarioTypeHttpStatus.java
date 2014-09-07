package com.michaelszymczak.speccare.specminer.specificationprovider;

import com.michaelszymczak.speccare.specminer.core.AmbiguousScenario;
import com.michaelszymczak.speccare.specminer.core.ExistingScenario;
import com.michaelszymczak.speccare.specminer.core.NotFoundScenario;
import com.michaelszymczak.speccare.specminer.core.Scenario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.*;

@Component
public class ScenarioTypeHttpStatus {
    public HttpStatus getStatus(Scenario scenario) {
        if (scenario instanceof ExistingScenario) {
            return OK;
        }
        if (scenario instanceof NotFoundScenario) {
            return NOT_FOUND;
        }
        if (scenario instanceof AmbiguousScenario) {
            return UNPROCESSABLE_ENTITY;
        }
        return INTERNAL_SERVER_ERROR;
    }
}
