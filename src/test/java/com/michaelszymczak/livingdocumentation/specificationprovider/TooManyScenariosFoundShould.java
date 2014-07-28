package com.michaelszymczak.livingdocumentation.specificationprovider;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TooManyScenariosFoundShould {

    @Test
    public void stringifyAccordingToThePassedMessage() throws Exception {
        TooManyScenariosFound exception = new TooManyScenariosFound("foo", new ArrayList<String>());
        assertEquals("com.michaelszymczak.livingdocumentation.specificationprovider.TooManyScenariosFound: foo", exception.toString());
    }

    @Test
    public void returnFeaturesPathsSeparatedByComma() throws Exception {
        TooManyScenariosFound exception = new TooManyScenariosFound("foo", Arrays.asList("foo/bar.feature", "bar/baz.feature"));
        assertEquals("foo/bar.feature,bar/baz.feature", exception.getFeaturePaths());
    }

    @Test
    public void returnNoPathsIfNoPathsPassed() throws Exception {
        TooManyScenariosFound exception = new TooManyScenariosFound("foo", new ArrayList<String>());
        assertEquals("", exception.getFeaturePaths());
    }
}