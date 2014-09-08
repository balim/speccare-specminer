package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.ResultLocator;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.FileVisitResult.CONTINUE;

class FilesystemFeatureFilesRetriever implements FeatureFilesRetriever {

    private final ResultLocator featuresDir;

    public FilesystemFeatureFilesRetriever(ResultLocator featuresDir) {
        this.featuresDir = featuresDir;
    }

    @Override
    public Map<String, List<String>> getFiles() throws IOException {
        Finder fileVisitor = new Finder(FileSystems.getDefault().getPathMatcher("glob:*.feature"));
        Files.walkFileTree(featuresDir.getFeaturesDirPath(), EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, fileVisitor);
        return fileVisitor.getFoundPaths();
    }



    private static class Finder extends SimpleFileVisitor<Path> {

        private final PathMatcher matcher;

        private final Map<String, List<String>> foundPaths = new HashMap<>();
        private final EncodingDetector encDetector = new EncodingDetector();

        Finder(PathMatcher matcher) {
            this.matcher = matcher;
        }

        public Map<String, List<String>> getFoundPaths() {
            return foundPaths;
        }

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
            Path name = path.getFileName();
            if (name != null && matcher.matches(name)) {
                foundPaths.put(path.toFile().getAbsolutePath(), encDetector.getContent(path));
            }
            return CONTINUE;
        }
    }
}
