package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.nio.file.Path;

public class FeaturesConfigurableDirectory implements FeaturesDirectory {
    public Path path;

    @Override
    public Path getPath() {
        return path;
    }
}
