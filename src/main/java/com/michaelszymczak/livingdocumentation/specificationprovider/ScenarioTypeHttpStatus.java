package com.michaelszymczak.livingdocumentation.specificationprovider;

import com.michaelszymczak.livingdocumentation.domain.AmbiguousScenario;
import com.michaelszymczak.livingdocumentation.domain.ExistingScenario;
import com.michaelszymczak.livingdocumentation.domain.NotFoundScenario;
import com.michaelszymczak.livingdocumentation.domain.Scenario;
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
