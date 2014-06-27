package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class FeaturesCreator {
    private TextFragmentProvider tfp;
    private FeatureFilesRetriever retriever;

    public FeaturesCreator(TextFragmentProvider tfp, FeatureFilesRetriever retriever) {
        this.tfp = tfp;
        this.retriever = retriever;
    }

    public List<Feature> create() throws IOException {
        List<Feature> features = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : retriever.getFiles().entrySet()) {
            features.add(new ExistingFeature(tfp, entry.getKey(), entry.getValue()));
        }
        return features;
    }
}
