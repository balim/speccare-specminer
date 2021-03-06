package com.michaelszymczak.speccare.specminer.featurefiles;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.LinkedHashSet;

public class ResultTemporaryLocatorShould {
    @Test public void
    createTemporaryDirectoryToStoreTemporaryFeaturesForUAT() throws IOException {
        assertThatPathExistsInTemporaryDirectory(locator.getFeaturesDirPath());
    }

    @Test public void
    createDirectoryDuringCreationAndDoNotChangeIt() throws IOException {
        Assert.assertSame(locator.getFeaturesDirPath(), locator.getFeaturesDirPath());
    }

    @Test public void
    tryToRemoveDirectoryOnExit() throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        assertThatPathWillBeDeletedOnExit(locator.getFeaturesDirPath());
    }

    @Test public void
    placePassedResultFileInFeaturesTemporaryDirectory() throws IOException {
        ResultTemporaryLocator ftd = new ResultTemporaryLocator("myResult.json");
        Assert.assertEquals(ftd.getFeaturesDirPath().resolve("myResult.json"), ftd.getResultFilePath());
    }

    private ResultTemporaryLocator locator;

    @Before public void setUp() throws IOException {
        locator = new ResultTemporaryLocator("result.json");
    }

    private void assertThatPathExistsInTemporaryDirectory(Path featuresTmpDirPath) {
        Assert.assertTrue(featuresTmpDirPath.toFile().exists());
        Assert.assertTrue(featuresTmpDirPath.toFile().getAbsolutePath().startsWith((System.getProperty("java.io.tmpdir"))));
    }

    // hack, but no idea how to test it otherwise
    private void assertThatPathWillBeDeletedOnExit(Path featuresTmpDirPath) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> exitHookClass = Class.forName("java.io.DeleteOnExitHook");
        Field privateField = exitHookClass.getDeclaredField("files");
        privateField.setAccessible(true);
        // java.io.DeleteOnExitHook::files field is of type LinkedHashSet<String>, at least in java 1.7.0_60
        @SuppressWarnings("unchecked") LinkedHashSet<String> filesToBeDeleted = (LinkedHashSet<String>) privateField.get(null);
        Assert.assertTrue(filesToBeDeleted.contains(featuresTmpDirPath.toFile().getAbsolutePath()));
    }
}
