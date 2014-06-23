package com.michaelszymczak.livingdocumentation.sandbox;

import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.*;
import static java.nio.file.FileVisitResult.*;
import java.util.*;

public class FinderTest {
    @Test
    public void shouldReturnBar() throws IOException {
        Finder fileVisitor = new Finder(FileSystems.getDefault().getPathMatcher("glob:*.txt"));
        String startingDir = "/tmp/z";
        Files.walkFileTree(Paths.get(startingDir), EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, fileVisitor);
        System.out.println(fileVisitor.getFoundPaths());
    }


    private static class Finder extends SimpleFileVisitor<Path> {

        private final PathMatcher matcher;

        private Map<String, List<String>> foundPaths = new HashMap<>();

        Finder(PathMatcher matcher) {
            this.matcher = matcher;
        }

        public Map<String, List<String>> getFoundPaths()
        {
            return foundPaths;
        }


        // Invoke the pattern matching
        // method on each path.
        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
            Path name = path.getFileName();
            if (name != null && matcher.matches(name)) {
                List<String> content = Files.readAllLines(path, Charset.defaultCharset());
                foundPaths.put(path.toFile().getAbsolutePath(), content);
            }
            return CONTINUE;
        }
    }
}
