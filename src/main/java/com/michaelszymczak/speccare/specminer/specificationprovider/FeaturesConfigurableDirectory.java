package com.michaelszymczak.speccare.specminer.specificationprovider;

import java.nio.file.Path;

public class FeaturesConfigurableDirectory implements FeaturesDirectory {
    private Path path;

    @Override
    public synchronized Path getPath() {
        return path;
    }

    public synchronized void setPath(Path path) {
        this.path = path;
    }
}
