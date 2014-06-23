package com.michaelszymczak.livingdocumentation.specificationprovider;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class FeatureFileRetrieverShould {

    @Test
    public void returnNoFileIfNoFeatureFiles() throws IOException {
        givenEmptyDirectory(featuresDir);
        assertThatNoFilenamesReturnedInGivenDirectory(retriever.getFiles(), featuresDir);

    }

//    TODO: Ready
//    @Test
//    public void findFeatureFilesBasedOnTheirExtensionAndIgnoreTheRest() throws IOException {
//        givenFilesInDirectory(featuresDir, new String[]{"foo.feature", "bar.feature", "baz.txt"});
//        assertThat(retriever.getFiles(), containsOnlyFilenamesInDirectory(featuresDir, new String[]{"foo.feature", "bar.feature"}));
//    }



    private Path featuresDir;
    private FeatureFileRetriever retriever;

    @Before
    public void setUp() throws IOException {
        featuresDir = Files.createTempDirectory("LivingDocumentationFeaturesTempDir");
        featuresDir.toFile().deleteOnExit();
        retriever = new FeatureFileRetriever(featuresDir);
    }

    private void givenEmptyDirectory(Path featuresDirectory) {
        assertEquals(0, featuresDirectory.toFile().list().length);
    }

    private void assertThatNoFilenamesReturnedInGivenDirectory(Map<String, List<String>> files, Path featuresDirectory) {
        assertThat(files, containsOnlyFilenamesInDirectory(featuresDirectory, new String[]{}));
    }

    private void givenFilesInDirectory(Path featuresDirectory, String[] filePaths) throws IOException {
        for (String filePath : filePaths) {
            Files.createFile(featuresDirectory.resolve(filePath)).toFile().deleteOnExit();
        }
    }

    private static Matcher<Map<String, List<String>>> containsOnlyFilenamesInDirectory(final Path featuresDirectory, final String[] expectedRelativeFilePaths) {
        final Set<String> expectedAbsolutePaths = new HashSet<>();
        for (String relativePath : expectedRelativeFilePaths) {
            expectedAbsolutePaths.add(featuresDirectory.resolve(relativePath).toFile().getAbsolutePath());
        }

        return new BaseMatcher<Map<String, List<String>>>() {
            @Override
            public boolean matches(final Object item) {
                Map<String, List<String>> actualResult = (Map<String, List<String>>) item;
                return actualResult.keySet().equals(expectedAbsolutePaths);
            }

            @Override
            public void describeTo(Description description) {
                description.appendValue(expectedAbsolutePaths.toString());
            }
            @Override
            public void describeMismatch(final Object item, Description description) {
                Map<String, List<String>> actualResult = (Map<String, List<String>>) item;
                description.appendText("was ").appendValue(actualResult.keySet().toString());
            }
        };
    }
}