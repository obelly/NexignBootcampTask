package org.example.kit;

import org.example.entity.CallType;
import org.example.entity.TariffType;

import java.time.Duration;

public class Calculator {
    static int checkUnlimited = 0;
    static int checkRegular = 0;

    private Calculator() {
    }

    public static double calculate(TariffType tariffType, Duration duration, CallType callType, Long totalTime) {
        double cost = 0;
        long amount = duration.toMinutes();
        switch (tariffType) {
            case UNLIMITED:
                if (totalTime > 300) {
                    checkUnlimited += amount;
                    if (checkUnlimited <= 300) {
                        cost = 0;
                    } else {
                        if (checkUnlimited - 300.0 - amount < 0) {
                            cost = checkUnlimited - 300.0;
                        } else {
                            cost = amount;
                        }
                    }
                } else {
                    cost = 0;
                }
                break;
            case PER_MINUTE:
                cost = amount * 1.5;
                break;
            case REGULAR:
                if (callType == CallType.OUTGOING) {
                    if (totalTime > 100) {
                        checkRegular += amount;
                        if (checkRegular <= 100) {
                            cost = amount * 0.5;
                        } else {
                            if (checkRegular - 100.0 - amount < 0) {
                                cost = (checkRegular - 100.0) * 1.5 + 50;
                            } else {
                                cost = amount * 1.5;
                            }
                        }
                    } else {
                        cost = amount * 0.5;
                    }
                } else {
                    cost = 0;
                }
                break;
        }
        return cost;
    }
}
