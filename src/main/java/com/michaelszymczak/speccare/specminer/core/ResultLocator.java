package com.michaelszymczak.speccare.specminer.core;

import java.nio.file.Path;

public interface ResultLocator {
    Path getFeaturesDirPath();

    Path getResultFilePath();
}
