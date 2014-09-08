package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.Feature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class FeaturesFromFilesCreator {
    private final FeatureFilesRetriever retriever;
    private final FeatureCreator creator;

    public FeaturesFromFilesCreator(TextFragmentProvider tfp, FeatureFilesRetriever retriever) {
        this.creator = new FeatureCreator(tfp);
        this.retriever = retriever;
    }

    public List<Feature> create() throws IOException {
        List<Feature> features = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : retriever.getFiles().entrySet()) {
            features.add(creator.create(entry.getValue(), entry.getKey()));
        }
        return features;
    }
}
