package com.michaelszymczak.speccare.specminer.domain;

import java.nio.file.Path;

public interface ResultLocator {
    Path getFeaturesDirPath();

    Path getResultFilePath();
}
