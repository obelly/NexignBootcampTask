package org.example.enums;

import java.time.format.DateTimeFormatter;

public enum DatePatternEnum {
    YYYY_MM_DD_HH_MM_SS_TOGETHER (DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
    HH_MM_SS (DateTimeFormatter.ofPattern("HH:mm:ss")),
    DEFAULT (DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    private final DateTimeFormatter formatter;

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    DatePatternEnum(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }
}
