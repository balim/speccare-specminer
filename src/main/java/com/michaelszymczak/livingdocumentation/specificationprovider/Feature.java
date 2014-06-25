package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Feature {

    private String name;
    private TextFragmentProvider tfp;
    private String path;
    private List<Scenario> scenarios;

    public Feature(TextFragmentProvider textFragmentProvider, String pathToFeatureFile, List<String> featureFileContent, List<Scenario> featureScenarios) {
        tfp = textFragmentProvider;
        name = extractName(featureFileContent);
        path = pathToFeatureFile;
        scenarios = featureScenarios;

    }

    private String extractName(List<String> featureFileContent) {
        ArrayList<String> names = tfp.getAllFragmentsThatFollows(featureFileContent, new String[]{"Feature:"});
        if (names.size() == 0) {
            throw new InvalidFeatureContentException("No 'Feature:' line in feature content: " + featureFileContent.toString());
        }
        if (names.size() > 1) {
            throw new InvalidFeatureContentException("Too many 'Feature:' lines in feature content: " + featureFileContent.toString());
        }
        return names.get(0);
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }
}
