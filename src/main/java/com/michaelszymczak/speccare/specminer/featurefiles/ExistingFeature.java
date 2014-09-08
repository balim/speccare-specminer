package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.Feature;

import java.util.Collections;
import java.util.List;

class ExistingFeature extends Feature {

    private final String name;
    private final String path;
    private final List<String> content;

    public ExistingFeature(String name, String pathToFeatureFile, List<String> featureFileContent) {
        this.name = name;
        this.path = pathToFeatureFile;
        this.content = Collections.unmodifiableList(featureFileContent);
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public List<String> getContent() {
        return content;
    }

}
