package org.example.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum CallTypeEnum {
    OUTGOING("01"),
    INCOMING("02");

    private final String number;
    private static final Map<String, CallTypeEnum> callTypeEnumMap = new HashMap<>();

    static {
        Arrays.stream(CallTypeEnum.values())
                .forEach(callTypeEnum -> callTypeEnumMap.put(callTypeEnum.getNumber(), callTypeEnum));
    }

    CallTypeEnum(String number) {
        this.number = number;
    }

    public static CallTypeEnum getByNumber(String number) {
        return callTypeEnumMap.get(number);
    }

    public String getNumber() {
        return number;
    }

}
