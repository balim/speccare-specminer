package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.ResultLocator;
import com.michaelszymczak.speccare.specminer.core.SourceNotFound;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocatorBasedResultSourceShould {
    private ResultLocator locator;
    private Path resultFilePath;
    private LocatorBasedResultSource source;

    @Test public void
    provideReadersBasedOnResultFilePath() throws IOException {
        Files.write(resultFilePath, "foo".getBytes());
        assertReaderContentEquals("foo", source.getSources().get(0));
    }

    @Test public void
    provideReaderFromFirstSource() throws IOException {
        Files.write(resultFilePath, "bar".getBytes());
        assertReaderContentEquals("bar", source.getFirstSource());
    }

    @Test public void
    complainThatSourceNotFoundIfLocatorCantProvideCorrectLocation() throws IOException {
        givenResultFileNotExist();
        exception.expect(SourceNotFound.class);
        source.getFirstSource();
    }

    private void givenResultFileNotExist() throws IOException {
        Files.delete(resultFilePath);
        Assert.assertFalse(Files.exists(resultFilePath));
    }

    @Before public void setUp() throws IOException {
        locator = new ResultTemporaryLocator("someResult.json");
        resultFilePath = locator.getResultFilePath();
        Files.createFile(resultFilePath);
        source = new LocatorBasedResultSource(locator);
    }

    @After public void tearDown() throws IOException {
        if (Files.exists(resultFilePath)) {
            Files.delete(resultFilePath);
        }
    }

    @Rule public ExpectedException exception = ExpectedException.none();

    private void assertReaderContentEquals(String expected, Reader reader) throws IOException {
        Assert.assertEquals(expected, readerToString(reader));
    }

    private  String readerToString(Reader reader) throws IOException {
        char[] chars = new char[64];
        StringBuffer buffer = new StringBuffer();
        int numChars;

        while ((numChars = reader.read(chars, 0, chars.length)) > 0) {
            buffer.append(chars, 0, numChars);
        }

        return buffer.toString();
    }

}
