package com.michaelszymczak.livingdocumentation.specificationprovider;


import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class FeaturesConfigurableDirectoryShould {
    @Test public void allowToChangePathAtRuntime() {
        FeaturesConfigurableDirectory fcd = new FeaturesConfigurableDirectory();
        fcd.path = Paths.get("/configurable/foo");
        Assert.assertEquals("/configurable/foo", fcd.getPath().toFile().getAbsolutePath());
    }
}
