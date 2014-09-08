package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.ResultLocator;

import java.nio.file.Path;
import java.nio.file.Paths;

class ResultRealLocator implements ResultLocator {
    private final Path featuresDirPath;
    private final Path resultFilePath;

    public ResultRealLocator(String featuresDirPath, String resultFilePath) {
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
