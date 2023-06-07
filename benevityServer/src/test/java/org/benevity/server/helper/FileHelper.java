package org.benevity.server.helper;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

public class FileHelper {
    public static String fromFile(String fileName) throws IOException {
        return IOUtils.toString(requireNonNull(FileUtils.class.getClassLoader().getResourceAsStream(fileName)), UTF_8).trim();
    }
}
