package org.example.entity;

import java.util.NoSuchElementException;
import java.util.Objects;

public enum TariffType {
    UNLIMITED("06"),
    PER_MINUTE("03"),
    REGULAR("11");

    private final String number;

    TariffType(String number) {
        this.number = number;
    }

    public static TariffType withNumber(String number) {
        for (TariffType type : values()) {
            if (Objects.equals(type.number, number)) {
                return type;
            }
        }
        throw new NoSuchElementException(
                "There's no TariffType with number " + number);
    }

    public String getNumber() {
        return number;
    }
}
