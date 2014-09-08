package com.michaelszymczak.speccare.specminer.core;

import com.michaelszymczak.speccare.specminer.featurefiles.TextFragmentProvider;

import java.util.Collections;
import java.util.List;

public class ExistingFeature extends Feature {

    private TextFragmentProvider tfp;
    private String name;
    private final String path;
    private final List<String> content;

    public ExistingFeature(String name, String pathToFeatureFile, List<String> featureFileContent) {
        this.path = pathToFeatureFile;
        this.content = Collections.unmodifiableList(featureFileContent);
        this.name = name;
    }

    @Deprecated
    public ExistingFeature(TextFragmentProvider textFragmentProvider, String pathToFeatureFile, List<String> featureFileContent) {
        this("", pathToFeatureFile, featureFileContent);
        name = extractName(textFragmentProvider, featureFileContent);
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

    private String extractName(TextFragmentProvider tfp, List<String> featureFileContent) {
        List<String> names = tfp.getAllFragmentsThatFollows(featureFileContent, new String[]{FEATURE_START});
        if (names.isEmpty()) {
            throw new InvalidFeatureContentException("No 'Feature:' line in feature content: " + featureFileContent.toString());
        }
        if (names.size() > 1) {
            throw new InvalidFeatureContentException("Too many 'Feature:' lines in feature content: " + featureFileContent.toString());
        }
        return names.get(0);
    }
}
