package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.Feature;
import com.michaelszymczak.speccare.specminer.featurefiles.ExistingFeature;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExistingFeatureShould {


    @Test public void
    storePathToFeatureFileSoThatItCanBeEasilyFindOnDiskLater() {
        Feature feature = new ExistingFeature(validName(), "/path/to/myFeature.feature", validFeatureContent());
        assertEquals("/path/to/myFeature.feature", feature.getPath());
    }

    @Test public void
    storeNameOfTheFeature() {
        Feature feature = new ExistingFeature("My feature", validPathToFeatureFile(), validFeatureContent());
        assertEquals("My feature", feature.getName());
    }

    @Test public void
    storeContentOfTheFeatureFileAsIsSoThatWeKnowTheExactContentOfTheOriginalFile() {
        List<String> content = Arrays.asList(
            "Feature: My feature",
            "  Next line"
        );

        Feature feature = new ExistingFeature(validName(), validPathToFeatureFile(), content);

        assertEquals(content, feature.getContent());
    }


    private String validName() {
        return "Foo feature";
    }

    private List<String> validFeatureContent() {
        return Arrays.asList("Feature: Foo feature");
    }

    private String validPathToFeatureFile() {
        return "/bar/foo.feature";
    }
}