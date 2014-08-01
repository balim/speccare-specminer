package com.michaelszymczak.speccare.specminer.specificationprovider;


import org.junit.Assert;
import org.junit.Test;

public class FeaturesDirectoryShould {
    @Test public void createPathFromPassedString() {
        FeaturesDirectory fd = new FeaturesRealDirectory("/path/to/dir");
        Assert.assertEquals("/path/to/dir", fd.getPath().toFile().getAbsolutePath());
    }
}
