package org.example.model;

import org.example.enums.CallTypeEnum;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ExportCallModel {
    CallTypeEnum callTypeEnum;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalTime duration;
    private double cost;

    public CallTypeEnum getCallTypeEnum() {
        return callTypeEnum;
    }

    public void setCallTypeEnum(CallTypeEnum callTypeEnum) {
        this.callTypeEnum = callTypeEnum;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
