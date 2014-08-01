package com.michaelszymczak.speccare.specminer.specificationprovider;

import java.nio.file.Path;

public class FeaturesConfigurableDirectory implements FeaturesDirectory {
    public Path path;

    @Override
    public Path getPath() {
        return path;
    }
}
