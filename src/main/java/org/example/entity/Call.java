package org.example.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

public class Call {

    private CallType callType;
    private Long phoneNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TariffType tariffType;
    private Duration duration;
    private double cost;

    public void setCost(double cost) {
        this.cost = cost;
    }

    public CallType getCallType() {
        return callType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public TariffType getTariffType() {
        return tariffType;
    }

    public Duration getDuration() {
        return duration;
    }

    public Long durationToMinutes() {
        return duration.toMinutes();
    }


    public double getCost() {
        return cost;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public Call(CallType callType, Long phoneNumber, LocalDateTime startTime, LocalDateTime endTime, TariffType tariffType) {
        this.callType = callType;
        this.phoneNumber = phoneNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tariffType = tariffType;
        this.duration = Duration.between(startTime, endTime);
    }

    @Override
    public String toString() {
        String str;
        Formatter f = new Formatter();
        str = f.format("%n|%10.10s|%20.20s|%20.20s|%10.10s|%10.10s|",
                        callType.getNumber(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(startTime),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(endTime),
                        DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.of(duration.toHoursPart(), duration.toMinutesPart(),
                                duration.toSecondsPart())), cost)
                .toString();
        f.close();
        return str;
    }
}
