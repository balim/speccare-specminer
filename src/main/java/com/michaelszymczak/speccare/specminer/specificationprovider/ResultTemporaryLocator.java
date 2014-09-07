package com.michaelszymczak.speccare.specminer.specificationprovider;

import com.michaelszymczak.speccare.specminer.core.ResultLocator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResultTemporaryLocator implements ResultLocator {

    private Path path;
    private Path resultFilePath;

    public ResultTemporaryLocator(String resultFile) throws IOException {
        path = Files.createTempDirectory("LivingDocumentationFeaturesTemporaryDirectory");
        path.toFile().deleteOnExit();
        this.resultFilePath = getFeaturesDirPath().resolve(resultFile);
    }

    @Override
    public Path getFeaturesDirPath() {
        return path;
    }

    @Override
    public Path getResultFilePath() {
        return resultFilePath;
    }
}
