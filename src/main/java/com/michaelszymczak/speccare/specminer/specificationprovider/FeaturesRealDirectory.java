package com.michaelszymczak.speccare.specminer.specificationprovider;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FeaturesRealDirectory implements FeaturesDirectory {
    private final Path featuresDirPath;
    private final Path resultFilePath;

    public FeaturesRealDirectory(String featuresDirPath, String resultFilePath) {
        this.featuresDirPath = Paths.get(featuresDirPath);
        this.resultFilePath = Paths.get(resultFilePath);
    }

    @Override
    public Path getFeaturesDirPath() {
        return featuresDirPath;
    }

    @Override
    public Path getResultFilePath() {
        return resultFilePath;
    }
}
