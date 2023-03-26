package org.example.command.export;

public interface ExportCommand<T> {
    void process(T model);
}
