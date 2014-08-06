package com.michaelszymczak.speccare.specminer.specificationprovider;

import org.codehaus.plexus.util.IOUtil;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

        private final Map<String, List<String>> foundPaths = new HashMap<>();

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
                List<String> content = Files.readAllLines(path, Charset.forName(getDetectedEncoding(path.toFile())));
                foundPaths.put(path.toFile().getAbsolutePath(), content);
            }
            return CONTINUE;
        }

        // From: http://www.programcreek.com/java-api-examples/index.php?api=org.mozilla.universalchardet.UniversalDetector
        // TODO: use http://mvnrepository.com/artifact/info.cukes/gherkin/2.7.3 instead
        private String getDetectedEncoding(File file) {
            InputStream is = null;
            String encoding = null;
            try {
                is = new FileInputStream(file);
                UniversalDetector detector = new UniversalDetector(null);
                byte[] buf = new byte[4096];
                int nread;
                while ((nread = is.read(buf)) > 0 && !detector.isDone()) {
                    detector.handleData(buf, 0, nread);
                }
                detector.dataEnd();
                encoding = detector.getDetectedCharset();
            } catch (IOException e) {
                // nothing to do
            } finally {
                IOUtil.close(is);
                if (encoding == null) {
                    return Charset.defaultCharset().name();
                }
            }
            return encoding;
        }
    }
}
