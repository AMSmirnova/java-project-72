package hexlet.code.util;

import hexlet.code.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.util.stream.Collectors;

public class Data {
    public static String readResourceFile(String fileName) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,
                StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    public static String normalizeUrl(String input) throws URISyntaxException, MalformedURLException {
        String normUrl = input.trim().toLowerCase();
        URL url = new URI(normUrl).toURL();
        var result = url.getProtocol() + "://" + url.getAuthority();
        return result;
    }
}
