package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.io.IOException;
import java.util.List;
import java.util.Map;

interface FeatureFilesRetriever {
    Map<String, List<String>> getFiles() throws IOException;
}
