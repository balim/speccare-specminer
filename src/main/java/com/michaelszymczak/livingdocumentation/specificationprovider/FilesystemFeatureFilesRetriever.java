package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static java.nio.file.FileVisitResult.CONTINUE;

class FilesystemFeatureFilesRetriever implements FeatureFilesRetriever {

    private final FeaturesDirectory featuresDir;

    public FilesystemFeatureFilesRetriever(FeaturesDirectory featuresDir) {
        this.featuresDir = featuresDir;
    }

    @Override
    public Map<String, List<String>> getFiles() throws IOException {
        Finder fileVisitor = new Finder(FileSystems.getDefault().getPathMatcher("glob:*.feature"));
        Files.walkFileTree(featuresDir.getPath(), EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, fileVisitor);
        return fileVisitor.getFoundPaths();
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
