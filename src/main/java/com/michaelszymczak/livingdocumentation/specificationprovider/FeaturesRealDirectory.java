package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FeaturesRealDirectory implements FeaturesDirectory {
    private Path path;

    public FeaturesRealDirectory(String dirPath) {
        path = Paths.get(dirPath);
    }

    @Override
    public Path getPath() {
        return path;
    }
}
