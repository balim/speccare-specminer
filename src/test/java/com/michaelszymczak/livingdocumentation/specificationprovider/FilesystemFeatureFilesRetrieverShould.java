package com.michaelszymczak.livingdocumentation.specificationprovider;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import static org.hamcrest.core.Is.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class FilesystemFeatureFilesRetrieverShould {

    @Test public void returnNoFileIfNoFeatureFiles() throws IOException {
        givenEmptyDirectory(featuresPath);
        assertThatNoFilenamesReturnedInGivenDirectory(retriever.getFiles(), featuresPath);

    }

    @Test public void findFeatureFilesBasedOnTheirExtensionAndIgnoreTheRest() throws IOException {
        givenFilesInDirectory(featuresPath, new String[]{"foo.feature", "bar.feature", "baz.txt"});
        assertThat(retriever.getFiles(), containsOnlyFilenamesInDirectory(featuresPath, new String[]{"foo.feature", "bar.feature"}));
    }

    @Test public void findFeatureFilesAlsoInSubdirectoriesRelativeToTheBaseDirectory() throws IOException {
        givenFilesInDirectory(featuresPath, new String[]{"bar/foo.feature", "foo/bar/baz/bar.feature", "foo/bar/baz/baz.feature"});
        assertThat(retriever.getFiles(), containsOnlyFilenamesInDirectory(featuresPath, new String[]{"bar/foo.feature", "foo/bar/baz/bar.feature", "foo/bar/baz/baz.feature"}));
    }

    @Test public void retrieveTheContentOfTheFile() throws IOException {
        givenFilesWithContentInDirectory(featuresPath, new String[]{"foo.feature", "bar.feature"}, new String[]{"Content of foo", "Content\nOf\nBAR"});
        assertThat(retriever.getFiles(), containsFilesWithContentInDirectory(featuresPath, new String[]{"foo.feature", "bar.feature"}, new String[]{"Content of foo", "Content\nOf\nBAR"}));
    }

    @Test public void returnAbsoluteFilePathAsKeyAndListOfLinesInsideTheFileAsValue() throws IOException {
        givenFilesWithContentInDirectory(featuresPath, new String[]{"foo/bar.feature"}, new String[]{"Content of bar"});
        assertThat(retriever.getFiles().get(featuresPath + "/foo/bar.feature").get(0), is("Content of bar"));
    }






    private Path featuresPath;
    private FilesystemFeatureFilesRetriever retriever;
    @Before public void setUp() throws IOException {
        FeaturesTemporaryDirectory ftd = new FeaturesTemporaryDirectory();
        retriever = new FilesystemFeatureFilesRetriever(ftd);
        featuresPath = ftd.getPath();
    }

    @After public void tearDown() throws IOException {
        Files.walkFileTree(featuresPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

        });
    }

    private void givenEmptyDirectory(Path featuresDirectory) {
        assertEquals(0, featuresDirectory.toFile().list().length);
    }

    private void assertThatNoFilenamesReturnedInGivenDirectory(Map<String, List<String>> files, Path featuresDirectory) {
        assertThat(files, containsOnlyFilenamesInDirectory(featuresDirectory, new String[]{}));
    }

    private void givenFilesInDirectory(Path featuresDirectory, String[] filePaths) throws IOException {
        givenFilesWithContentInDirectory(featuresDirectory, filePaths, new String[] {});
    }

    private void givenFilesWithContentInDirectory(Path featuresDirectory, String[] filePaths, String[] contentOfEachFile) throws IOException {
        String filePath;
        File createdFile;
        FileWriter fw;
        for (int i = 0; i < filePaths.length; i++) {
            filePath = filePaths[i];
            Files.createDirectories(featuresDirectory.resolve(filePath).getParent()).toFile();
            createdFile = Files.createFile(featuresDirectory.resolve(filePath)).toFile();
            if (contentOfEachFile.length > i && contentOfEachFile[i] != null) {
                fw = new FileWriter(createdFile);
                fw.write(contentOfEachFile[i]);
                fw.close();
            }
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
                @SuppressWarnings("unchecked") Map<String, List<String>> actualResult = (Map<String, List<String>>) item;
                return actualResult.keySet().equals(expectedAbsolutePaths);
            }

            @Override
            public void describeTo(Description description) {
                description.appendValue(expectedAbsolutePaths.toString());
            }
            @Override
            public void describeMismatch(final Object item, Description description) {
                @SuppressWarnings("unchecked") Map<String, List<String>> actualResult = (Map<String, List<String>>) item;
                description.appendText("was ").appendValue(actualResult.keySet().toString());
            }
        };
    }

    private static Matcher<Map<String, List<String>>> containsFilesWithContentInDirectory(final Path featuresDirectory, final String[] expectedRelativeFilePaths, final String[] expectedContentOfEachFile) throws IOException {
        final Map<String, List<String>> expectedFilesWithContent = new HashMap<>();
        String absolutePath;
        List<String> content;
        for (int i = 0; i < expectedRelativeFilePaths.length; i++) {
            absolutePath = featuresDirectory.resolve(expectedRelativeFilePaths[i]).toFile().getAbsolutePath();
            Path pathToFile = featuresDirectory.resolve(expectedRelativeFilePaths[i]);
            content = Files.readAllLines(pathToFile, Charset.defaultCharset());
            expectedFilesWithContent.put(absolutePath, content);
        }

        return new BaseMatcher<Map<String, List<String>>>() {
            @Override
            public boolean matches(final Object item) {
                @SuppressWarnings("unchecked") Map<String, List<String>> actualResult = (Map<String, List<String>>) item;
                return expectedFilesWithContent.equals(actualResult);
            }

            @Override
            public void describeTo(Description description) {
                description.appendValue(expectedFilesWithContent.toString());
            }
            @Override
            public void describeMismatch(final Object item, Description description) {
                @SuppressWarnings("unchecked") Map<String, List<String>> actualResult = (Map<String, List<String>>) item;
                description.appendText("was ").appendValue(actualResult.toString());
            }
        };
    }
}