package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class FeaturesCreator {
    private FeatureFilesRetriever retriever;

    public FeaturesCreator(FeatureFilesRetriever retriever) {

        this.retriever = retriever;
    }

    public List<Feature> create() throws IOException {
        List<Feature> features = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : retriever.getFiles().entrySet()) {
            features.add(new Feature(new TextFragmentProvider(), entry.getKey(), entry.getValue()));
        }
        return features;
    }
}
