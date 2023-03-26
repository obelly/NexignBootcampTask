package org.example.command.parser.impl;

import org.example.command.parser.ParseCommand;
import org.example.enums.CallTypeEnum;
import org.example.enums.DatePatternEnum;
import org.example.enums.TariffTypeEnum;
import org.example.model.CallDataRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParseCommandImpl implements ParseCommand<CallDataRecord> {

    private static final String REGEX = ", ";

    @Override
    public List<CallDataRecord> process(String file) {
        List<CallDataRecord> callDataRecordList = new ArrayList<>();
        var path = Paths.get(file);
        try {
            var lines = Files.readAllLines(path);
            for (var line : lines) {
                var fields = line.split(REGEX);
                var callDataRecord = new CallDataRecord();
                callDataRecord.setCallType(CallTypeEnum.getByNumber(fields[0]));
                callDataRecord.setPhoneNumber(Long.parseLong(fields[1]));
                callDataRecord.setStartTime(LocalDateTime.parse(fields[2], DatePatternEnum.YYYY_MM_DD_HH_MM_SS_TOGETHER.getFormatter()));
                callDataRecord.setEndTime(LocalDateTime.parse(fields[3], DatePatternEnum.YYYY_MM_DD_HH_MM_SS_TOGETHER.getFormatter()));
                callDataRecord.setTariffType(TariffTypeEnum.getByNumber(fields[4]));
                callDataRecordList.add(callDataRecord);
            }
        } catch (IOException e) {
            System.out.printf("Произошла ошибка при парсинге файла: %s%n", file);
            throw new RuntimeException(e);
        }

        return callDataRecordList;
    }
}
