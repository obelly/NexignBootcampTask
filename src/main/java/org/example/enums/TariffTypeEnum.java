package org.example.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum TariffTypeEnum {
    UNLIMITED("06",1.0 ),
    PER_MINUTE("03", 1.5),
    REGULAR("11", 0.5);
    private final String number;
    private final double priceRubMin;
    private static final Map<String, TariffTypeEnum> tariffTypeEnumMap = new HashMap<>();

    static {
        Arrays.stream(TariffTypeEnum.values())
                .forEach(callTypeEnum -> tariffTypeEnumMap.put(callTypeEnum.getNumber(), callTypeEnum));
    }

    TariffTypeEnum(String number, double priceRubMin) {
        this.number = number;
        this.priceRubMin = priceRubMin;
    }

    public static TariffTypeEnum getByNumber(String number) {
        return tariffTypeEnumMap.get(number);
    }

    public String getNumber() {
        return number;
    }

    public double getPriceRubMin() {
        return priceRubMin;
    }
}
