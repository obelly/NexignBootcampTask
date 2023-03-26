package org.example.command.parser;

import java.util.List;

public interface ParseCommand<T> {
    List<T> process(String file);
}
