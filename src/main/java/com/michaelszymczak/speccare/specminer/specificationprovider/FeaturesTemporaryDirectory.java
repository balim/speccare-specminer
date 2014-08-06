package com.michaelszymczak.speccare.specminer.specificationprovider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FeaturesTemporaryDirectory implements FeaturesDirectory {

    private Path path;
    private Path resultFilePath;

    public FeaturesTemporaryDirectory(String resultFile) throws IOException {
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
