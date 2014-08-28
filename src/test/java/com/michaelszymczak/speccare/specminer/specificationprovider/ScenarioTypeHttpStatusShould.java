package com.michaelszymczak.speccare.specminer.specificationprovider;

import static org.springframework.http.HttpStatus.*;

import com.michaelszymczak.speccare.specminer.domain.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ScenarioTypeHttpStatusShould {

    @Test public void beOkForExistingFeature() {
        Assert.assertEquals(OK, scenarioTypeHttpStatus.getStatus(existingScenario));
    }

    @Test public void beNotFoundForNotFoundScenario() {
        Assert.assertEquals(NOT_FOUND, scenarioTypeHttpStatus.getStatus(notFoundScenario));
    }

    @Test public void beUnprocessableEntityForAmbiguousScenario() {
        Assert.assertEquals(UNPROCESSABLE_ENTITY, scenarioTypeHttpStatus.getStatus(ambiguousScenario));
    }

    @Test public void beServerErrorForUnknownScenario() {
        Assert.assertEquals(INTERNAL_SERVER_ERROR, scenarioTypeHttpStatus.getStatus(unknownScenario));
    }

    private final ScenarioTypeHttpStatus scenarioTypeHttpStatus = new ScenarioTypeHttpStatus();
    private final ExistingScenario existingScenario = ExistingScenarioBuilder.use().build();
    private final NotFoundScenario notFoundScenario = Scenario.getNotFound();
    private final AmbiguousScenario ambiguousScenario = AmbiguousScenarioBuilder.use().addFeaturePath("a.feature").addFeaturePath("b.feature").build();
    private final Scenario unknownScenario = new Scenario() {
        @Override
        public String getName() {
            return null;
        }

        @Override
        public Feature getFeature() {
            return null;
        }

        @Override
        public List<String> getContent() {
            return null;
        }

        @Override
        public ResultStatus getResult() { return null; }
    };

}
