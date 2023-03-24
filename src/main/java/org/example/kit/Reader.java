package org.example.kit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Reader {
    public List<String[]> read(String url) {
        try (var lines = Files.lines(Path.of(url))) {

            return lines.map(s -> s.split(", ")).collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
