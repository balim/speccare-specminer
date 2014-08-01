package com.michaelszymczak.speccare.specminer.cucumberSteps;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

@Component
class TemporaryFileCreator {
    public java.nio.file.Path createInDirWithContent(String featuresDir, String featureFileName, String content) throws IOException {
        File featureFile = Paths.get(featuresDir).resolve(featureFileName).toFile();
        featureFile.createNewFile();
        featureFile.deleteOnExit();
        PrintWriter pw = new PrintWriter(featureFile);
        pw.println(content);
        pw.close();
        return featureFile.toPath();
    }
}
