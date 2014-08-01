package com.michaelszymczak.speccare.specminer.specificationprovider;

import java.util.ArrayList;
import java.util.List;

public class TextFragmentProvider {
    public ArrayList<String> getAllFragmentsThatFollows(List<String> scenarioContent, String[] availableStarts) {
        String foundFragment;
        boolean isInMultilineQuotation = false;

        ArrayList<String> scenarioNames = new ArrayList<>();
        for(String line : scenarioContent) {
            foundFragment = null;
            if (returnStringFollowingAnyOf(line, new String[]{"\"\"\""}) != null) {
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
