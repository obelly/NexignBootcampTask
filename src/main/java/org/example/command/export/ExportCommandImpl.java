package org.example.command.export;

import org.example.model.ExportCallDataModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportCommandImpl implements ExportCommand<ExportCallDataModel> {
    private final String FILE_NAME = "report_cdr_%s.txt";
    private final String ROOT_DIRECTORY = "reports";
    @Override
    public void process(ExportCallDataModel model) {
        var fileName = String.format(FILE_NAME, model.getPhoneNumber());
        var dir = new File(ROOT_DIRECTORY);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                System.out.printf("Не удалось создать директорию: %s", ROOT_DIRECTORY);
                return;
            }
        }

        var file = new File(dir, fileName);
        if (file.exists()) {
            if (!file.delete()) {
                System.out.printf("Не удалось удалить файл: %s", fileName);
                return;
            }
        }

        try {
            if (!file.createNewFile()) {
                System.out.printf("Не удалось создать файл: %s", fileName);
                return;
            }
        } catch (IOException e) {
            System.out.printf("Ошибка при создании файла: %s", fileName);
            return;
        }

        try (var writer = new FileWriter(file);
             var bw = new BufferedWriter(writer)) {
            bw.write(model.toTableString());
        } catch (IOException e) {
            System.out.printf("Произошла ошибка при выгрузке файла: %s%n", fileName);
            throw new RuntimeException(e);
        }
    }
}
