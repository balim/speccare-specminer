package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.Feature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FeaturesFromFilesCreatorShould {

    private FeatureFilesRetrieverStub retriever;
    private FeaturesFromFilesCreator fc;

    @Test public void createFeatureWithDataFromFeatureFilesRetriever() throws IOException {
        givenRetrieverReturningOneFeatureFile();
        Feature feature = getFirstFeatureFeatureCreatorCreates();
        assertThatAFeatureWithTheRightDataCreated(feature);
    }

    @Test public void createAsManyFeaturesAsFilesRetrieved() throws IOException {
        givenRetrieverReturningFiveFeatureFiles();
        Assert.assertEquals(5, fc.create().size());
    }

    @Test public void createFeaturesRegardlessOfTheirPositionInRetrievedFiles() throws IOException {
        givenRetrieverReturningFourFeatureFilesWithTheThirdContaining("/path/to/Third.feature",
                Arrays.asList("Feature: My third feature", "  Next line of the third feature"));

        assertThatOneFeatureMeetsCriteria(fc.create(), "/path/to/Third.feature",
                Arrays.asList("Feature: My third feature", "  Next line of the third feature"));
    }


    private void assertThatOneFeatureMeetsCriteria(List<Feature> features, String path, List<String> content) {
        int featuresMeetingCriteria = 0;
        for (Feature f : features) {
            if (f.getPath().equals(path) && f.getContent().equals(content)) {
                featuresMeetingCriteria++;
            }
        }
        Assert.assertEquals(1, featuresMeetingCriteria);
    }

    private void givenRetrieverReturningFourFeatureFilesWithTheThirdContaining(String pathToFeatureFile, List<String> contentOfTheFeatureFile) {
        List<String> defaultContent = Arrays.asList("Feature: Bar feature", "  Next line");
        retriever.files.put("1.feature", defaultContent);
        retriever.files.put("2.feature", defaultContent);
        retriever.files.put(pathToFeatureFile, contentOfTheFeatureFile);
        retriever.files.put("4.feature", defaultContent);
    }

    private void assertThatAFeatureWithTheRightDataCreated(Feature feature) {
        Assert.assertEquals("Bar feature", feature.getName());
        Assert.assertEquals("/foo/bar.feature", feature.getPath());
        Assert.assertEquals(Arrays.asList("Feature: Bar feature", "  Next line"), feature.getContent());
    }

    private Feature getFirstFeatureFeatureCreatorCreates() throws IOException {
        return fc.create().get(0);
    }

    private void givenRetrieverReturningOneFeatureFile() {
        retriever.files.put("/foo/bar.feature", Arrays.asList("Feature: Bar feature", "  Next line"));
    }

    private void givenRetrieverReturningFiveFeatureFiles() {
        retriever.files.put("Feature1.feature", Arrays.asList("Feature: Foo feature"));
        retriever.files.put("Feature2.feature", Arrays.asList("Feature: Foo feature"));
        retriever.files.put("Feature3.feature", Arrays.asList("Feature: Foo feature"));
        retriever.files.put("Feature4.feature", Arrays.asList("Feature: Foo feature"));
        retriever.files.put("Feature5.feature", Arrays.asList("Feature: Foo feature"));
    }

    @Before public void setUp() {
        retriever = new FeatureFilesRetrieverStub();
        fc = new FeaturesFromFilesCreator(new TextFragmentProvider(), retriever);
    }
}