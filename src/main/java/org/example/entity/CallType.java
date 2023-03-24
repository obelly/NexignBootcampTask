package org.example.entity;

import java.util.NoSuchElementException;
import java.util.Objects;

public enum CallType {

    OUTGOING("01"),
    INCOMING("02");

    private final String number;

    CallType(String number) {
        this.number = number;
    }

    public static CallType withNumber(String number) {
        for (CallType type : values()) {
            if (Objects.equals(type.number, number)) {
                return type;
            }
        }
        throw new NoSuchElementException(
                "There's no CallType with number " + number);
    }

    public String getNumber() {
        return number;
    }
}
