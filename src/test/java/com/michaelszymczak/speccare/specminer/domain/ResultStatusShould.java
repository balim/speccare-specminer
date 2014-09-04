package com.michaelszymczak.speccare.specminer.domain;

import org.junit.Assert;
import org.junit.Test;


public class ResultStatusShould {
    @Test public void
    stringifyItselfSoThatCanBeUsedInJsonResponse() {
        assertToStringEquals("failed", ResultStatus.FAILED);
        assertToStringEquals("notfound", ResultStatus.NOT_FOUND);
        assertToStringEquals("ignored", ResultStatus.IGNORED);
        assertToStringEquals("skipped", ResultStatus.SKIPPED);
        assertToStringEquals("ambiguous", ResultStatus.AMBIGUOUS);
        assertToStringEquals("unknown", ResultStatus.UNKNOWN);
        assertToStringEquals("passed", ResultStatus.PASSED);
        assertToStringEquals("found", ResultStatus.FOUND);
    }

    @Test public void
    createEnumFromString() {
        Assert.assertEquals(ResultStatus.FAILED, ResultStatus.of("failed"));
        Assert.assertEquals(ResultStatus.NOT_FOUND, ResultStatus.of("notfound"));
        Assert.assertEquals(ResultStatus.IGNORED, ResultStatus.of("ignored"));
        Assert.assertEquals(ResultStatus.SKIPPED, ResultStatus.of("skipped"));
        Assert.assertEquals(ResultStatus.AMBIGUOUS, ResultStatus.of("ambiguous"));
        Assert.assertEquals(ResultStatus.UNKNOWN, ResultStatus.of("unknown"));
        Assert.assertEquals(ResultStatus.PASSED, ResultStatus.of("passed"));
        Assert.assertEquals(ResultStatus.FOUND, ResultStatus.of("found"));
    }

    @Test public void
    createEnumFromStringBasedOnStringEqualityNotIdentity() {
        Assert.assertEquals(ResultStatus.PASSED, ResultStatus.of(new String ("passed")));
    }

    @Test public void
    inFallbackModeReturnUnknownIfTriesToCreateUsingIllegalName() {
        Assert.assertEquals(ResultStatus.UNKNOWN, ResultStatus.ofFallback("wrong"));
    }

    @Test public void
    inFallbackModeReturnSameAsInNormalModeIfFound() {
        Assert.assertEquals(ResultStatus.PASSED, ResultStatus.ofFallback("passed"));
    }

    @Test(expected = IllegalArgumentException.class) public void
    throwExceptionIfIllegalStringUsedToCreateEnum() {
        ResultStatus.of("wrong");
    }

    @Test(expected = IllegalArgumentException.class) public void
    throwExceptionIfINullUsedToCreateEnum() {
        ResultStatus.of(null);
    }

    private void assertToStringEquals(String expectedOutput, ResultStatus status) {
        Assert.assertEquals(expectedOutput, status.toString());
    }
}
