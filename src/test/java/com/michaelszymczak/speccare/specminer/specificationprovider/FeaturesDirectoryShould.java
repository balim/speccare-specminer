package com.michaelszymczak.speccare.specminer.specificationprovider;


import org.junit.Assert;
import org.junit.Test;

public class FeaturesDirectoryShould {
    @Test public void
    providePathToFeatureFiles() {
        FeaturesDirectory fd = new FeaturesRealDirectory("/path/to/features", "foo");
        Assert.assertEquals("/path/to/features", fd.getFeaturesDirPath().toFile().getAbsolutePath());
    }

    @Test public void
    providePathToResultFile() {
        FeaturesDirectory fd = new FeaturesRealDirectory("/path/to/features", "/path/to/result.json");
        Assert.assertEquals("/path/to/result.json", fd.getResultFilePath().toFile().getAbsolutePath());
    }
}
