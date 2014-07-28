package com.michaelszymczak.livingdocumentation.specificationprovider;


import java.util.List;

class TooManyScenariosFound extends RuntimeException {
    private final List<String> featurePaths;
    public TooManyScenariosFound(String s, List<String> featurePaths) {
        super(s);
        this.featurePaths = featurePaths;
    }

    public String getFeaturePaths() {
        StringBuilder message = new StringBuilder();
        for (String path : featurePaths) {
            message.append(path);
            message.append(",");
        }
        String result = message.toString();
        if (result.length() == 0) return "";
        return result.substring(0, result.length()-1);
    }
}
