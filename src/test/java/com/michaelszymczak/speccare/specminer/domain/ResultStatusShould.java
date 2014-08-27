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

    private void assertToStringEquals(String expectedOutput, ResultStatus status) {
        Assert.assertEquals(expectedOutput, status.toString());
    }
}
