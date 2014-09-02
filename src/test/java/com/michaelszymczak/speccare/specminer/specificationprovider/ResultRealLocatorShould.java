package com.michaelszymczak.speccare.specminer.specificationprovider;


import com.michaelszymczak.speccare.specminer.domain.ResultLocator;
import org.junit.Assert;
import org.junit.Test;

public class ResultRealLocatorShould {
    @Test public void
    providePathToFeatureFiles() {
        ResultLocator fd = new ResultRealLocator("/path/to/features", "foo");
        Assert.assertEquals("/path/to/features", fd.getFeaturesDirPath().toFile().getAbsolutePath());
    }

    @Test public void
    providePathToResultFile() {
        ResultLocator fd = new ResultRealLocator("/path/to/features", "/path/to/result.json");
        Assert.assertEquals("/path/to/result.json", fd.getResultFilePath().toFile().getAbsolutePath());
    }
}
