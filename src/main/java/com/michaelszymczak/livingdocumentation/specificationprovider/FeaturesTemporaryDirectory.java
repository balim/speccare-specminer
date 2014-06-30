package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FeaturesTemporaryDirectory implements FeaturesDirectory {

    private Path path;

    public FeaturesTemporaryDirectory() throws IOException {
        path = Files.createTempDirectory("LivingDocumentationFeaturesTemporaryDirectory");
        path.toFile().deleteOnExit();
    }

    @Override
    public Path getPath() {
        return path;
    }
}
