package com.michaelszymczak.speccare.specminer.featurefiles;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FeatureFilesRetrieverStub implements FeatureFilesRetriever {

    final Map<String, List<String>> files = new HashMap<>();

    @Override
    public Map<String, List<String>> getFiles() throws IOException {
        return files;
    }
}
