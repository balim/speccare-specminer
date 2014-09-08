package com.michaelszymczak.speccare.specminer.featurefiles;

import java.util.ArrayList;
import java.util.List;

public class TextFragmentProvider {
    public List<String> getAllFragmentsThatFollows(List<String> scenarioContent, String[] availableStarts) {
        String foundFragment;
        boolean isInMultilineQuotation = false;

        List<String> scenarioNames = new ArrayList<>();
        for(String line : scenarioContent) {
            foundFragment = null;
            if (isMultilineQuotation(line)) {
                isInMultilineQuotation = !isInMultilineQuotation;
            }
            if (!isInMultilineQuotation) {
                foundFragment = returnStringFollowingAnyOf(line, availableStarts);
            }
            if (null != foundFragment) {
                scenarioNames.add(foundFragment);
            }
        }

        return scenarioNames;
    }

    public boolean isMultilineQuotation(String line) {
        return returnStringFollowingAnyOf(line, new String[]{"\"\"\""}) != null;
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
