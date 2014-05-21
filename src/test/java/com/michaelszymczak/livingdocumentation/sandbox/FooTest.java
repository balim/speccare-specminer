package com.michaelszymczak.livingdocumentation.sandbox;

import org.junit.Test;

import org.junit.Assert;

public class FooTest {
    @Test
    public void shouldReturnBar() {
        Assert.assertSame("bar", new Foo().getBar());
    }
}
