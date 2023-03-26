package org.example.model;

import org.example.enums.CallTypeEnum;
import org.example.enums.TariffTypeEnum;

import java.time.LocalDateTime;

public class CallDataRecord {

    private CallTypeEnum callType;
    private Long phoneNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TariffTypeEnum tariffType;

    public CallDataRecord() {}

    public CallTypeEnum getCallType() {
        return callType;
    }

    public void setCallType(CallTypeEnum callType) {
        this.callType = callType;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public TariffTypeEnum getTariffType() {
        return tariffType;
    }

    public void setTariffType(TariffTypeEnum tariffType) {
        this.tariffType = tariffType;
    }
}
