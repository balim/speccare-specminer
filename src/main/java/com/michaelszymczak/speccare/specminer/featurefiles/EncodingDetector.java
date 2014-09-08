package com.michaelszymczak.speccare.specminer.featurefiles;

import org.codehaus.plexus.util.IOUtil;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

// From: http://www.programcreek.com/java-api-examples/index.php?api=org.mozilla.universalchardet.UniversalDetector
class EncodingDetector {

    public List<String> getContent(Path path) throws IOException {
        return Files.readAllLines(path, Charset.forName(detect(path.toFile())));
    }

    private String detect(File file) throws IOException {
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
        } finally {
            IOUtil.close(is);
            if (encoding == null) {
                encoding = Charset.defaultCharset().name();
            }
        }
        return encoding;
    }
}
