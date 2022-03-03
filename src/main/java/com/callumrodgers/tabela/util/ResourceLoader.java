package com.callumrodgers.tabela.util;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ResourceLoader {

    public static URL get(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return ResourceLoader.class.getResource(path);
    }

    public static InputStream getAsStream(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return ResourceLoader.class.getResourceAsStream(path);
    }

    public static Image getImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(get(path));
    }

    public static void loadFontFamily(String folderPath) {
        boolean successful;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            for (String fileName : getResourceFiles(folderPath)) {
                String filePath = folderPath + fileName;
                if (filePath.endsWith(".ttf")) {
                    Font newFont = Font.createFont(Font.TRUETYPE_FONT, getAsStream(filePath));
                    successful = ge.registerFont(newFont);
                    if (! successful) {
                        System.err.println("Could not register font: '" + newFont + "'.");
                    }
                }
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getResourceFiles(String path) throws IOException {
        List<String> filenames = new ArrayList<>();
        try (
                InputStream in = getResourceAsStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;

            while ((resource = br.readLine()) != null) {
                filenames.add(resource);
            }
        }

        return filenames;
    }

    private static InputStream getResourceAsStream(String resource) {
        final InputStream in
                = getContextClassLoader().getResourceAsStream(resource);

        return in == null ? ResourceLoader.class.getResourceAsStream(resource) : in;
    }

    private static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }


}
