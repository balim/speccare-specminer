package com.michaelszymczak.speccare.specminer.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.michaelszymczak.speccare.specminer.core.ResultStatus.*;

public class ResultAggregateShould {
    @Test public void
    returnNotFoundResultIfNoResults() {
        Assert.assertEquals(NOT_FOUND, aggregate.result());
    }

    @Test public void
    returnTheResultIfOneResult() {
        aggregate.add(PASSED);
        Assert.assertEquals(PASSED, aggregate.result());
    }

    @Test public void
    returnAmbiguousResultIfAtLeastTwoDifferentResults() {
        aggregate.add(PASSED);
        aggregate.add(FAILED);
        Assert.assertEquals(AMBIGUOUS, aggregate.result());
    }

    @Test public void
    returnTheResultIfAllTheSameResults() {
        aggregate.add(FAILED);
        aggregate.add(FAILED);
        Assert.assertEquals(FAILED, aggregate.result());
    }



    private ResultAggregate aggregate;
    @Before public void setUp() {
        aggregate = new ResultAggregate();
    }
}
