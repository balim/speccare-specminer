package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Feature {

    public static final String FEATURE_START = "Feature:";

    private final String name;
    private final TextFragmentProvider tfp;
    private final String path;
    private final List<String> content;

    public Feature(TextFragmentProvider textFragmentProvider, String pathToFeatureFile, List<String> featureFileContent) {
        tfp = textFragmentProvider;
        path = pathToFeatureFile;
        content = featureFileContent;
        name = extractName(featureFileContent);
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

    private String extractName(List<String> featureFileContent) {
        ArrayList<String> names = tfp.getAllFragmentsThatFollows(featureFileContent, new String[]{FEATURE_START});
        if (names.size() == 0) {
            throw new InvalidFeatureContentException("No 'Feature:' line in feature content: " + featureFileContent.toString());
        }
        if (names.size() > 1) {
            throw new InvalidFeatureContentException("Too many 'Feature:' lines in feature content: " + featureFileContent.toString());
        }
        return names.get(0);
    }
}
