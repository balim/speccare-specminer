package com.michaelszymczak.speccare.specminer.domain;

import org.junit.Assert;
import org.junit.Test;

import static com.michaelszymczak.speccare.specminer.domain.ResultStatus.*;


public class ResultStatusShould {
    @Test public void
    stringifyItselfSoThatCanBeUsedInJsonResponse() {
        assertToStringEquals("failed", FAILED);
        assertToStringEquals("notfound", NOT_FOUND);
        assertToStringEquals("ignored", IGNORED);
        assertToStringEquals("skipped", SKIPPED);
        assertToStringEquals("ambiguous", AMBIGUOUS);
        assertToStringEquals("unknown", UNKNOWN);
        assertToStringEquals("passed", PASSED);
        assertToStringEquals("found", FOUND);
    }

    @Test public void
    createEnumFromString() {
        Assert.assertEquals(FAILED, of("failed"));
        Assert.assertEquals(NOT_FOUND, of("notfound"));
        Assert.assertEquals(IGNORED, of("ignored"));
        Assert.assertEquals(SKIPPED, of("skipped"));
        Assert.assertEquals(AMBIGUOUS, of("ambiguous"));
        Assert.assertEquals(UNKNOWN, of("unknown"));
        Assert.assertEquals(PASSED, of("passed"));
        Assert.assertEquals(FOUND, of("found"));
    }

    @Test public void
    createEnumFromStringBasedOnStringEqualityNotIdentity() {
        Assert.assertEquals(PASSED, of(new String("passed")));
    }

    @Test public void
    inFallbackModeReturnUnknownIfTriesToCreateUsingIllegalName() {
        Assert.assertEquals(UNKNOWN, ofFallback("wrong"));
    }

    @Test public void
    inFallbackModeReturnSameAsInNormalModeIfFound() {
        Assert.assertEquals(PASSED, ofFallback("passed"));
    }

    @Test(expected = IllegalArgumentException.class) public void
    throwExceptionIfIllegalStringUsedToCreateEnum() {
        of("wrong");
    }

    @Test(expected = IllegalArgumentException.class) public void
    throwExceptionIfINullUsedToCreateEnum() {
        of(null);
    }

    private void assertToStringEquals(String expectedOutput, ResultStatus status) {
        Assert.assertEquals(expectedOutput, status.toString());
    }
}
