package com.michaelszymczak.speccare.specminer.specificationprovider;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FeaturesRealDirectory implements FeaturesDirectory {
    private final Path path;

    public FeaturesRealDirectory(String dirPath) {
        path = Paths.get(dirPath);
    }

    @Override
    public Path getPath() {
        return path;
    }
}
