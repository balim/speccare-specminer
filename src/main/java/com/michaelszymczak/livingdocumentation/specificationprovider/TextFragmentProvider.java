package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.util.ArrayList;
import java.util.List;

class TextFragmentProvider {
    ArrayList<String> getAllFragmentsThatFollows(List<String> scenarioContent, String[] availableStarts) {
        ArrayList<String> scenarioNames = new ArrayList<>();
        for(String line : scenarioContent) {
            String foundFragment = returnStringFollowingAnyOf(line, availableStarts);
            if (null != foundFragment) {
                scenarioNames.add(foundFragment);
            }
        }
        return scenarioNames;
    }

    public String returnStringFollowingAnyOf(String line, String[] startingStrings) {
        line = line.trim();
        for (String start : startingStrings) {
            if (line.startsWith(start)) {
                return line.substring(start.length()).trim();
            }
        }
        return null;
    }
}
