package org.example.model;

import org.example.enums.CallTypeEnum;
import org.example.enums.DatePatternEnum;
import org.example.enums.TariffTypeEnum;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class ExportCallDataModel {
    private CallTypeEnum callType;
    private Long phoneNumber;
    private TariffTypeEnum tariffType;
    private List<ExportCallModel> exportCallModels;
    private double cost;

    public String toTableString() {
        var lineSeparator = "----------------------------------------------------------------------------\n";
        var sb = new StringBuilder();
        sb.append("Tariff index: ").append(this.tariffType.getNumber()).append("\n");
        sb.append(lineSeparator);
        sb.append("Report for phone number ").append(this.phoneNumber).append(":\n");
        sb.append(lineSeparator);

        var headerFormat = "| %-9s | %-19s | %-19s | %-8s | %-6s |\n";
        sb.append(String.format(headerFormat, "Call Type", "Start Time", "End Time", "Duration", "Cost"));
        sb.append(lineSeparator);

        var df = new DecimalFormat("0.00");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));

        var rowFormat = "|     %-5s | %-19s | %-19s | %-8s | %-6s |\n";
        this.exportCallModels.forEach(exportCallModel ->
                sb.append(String.format(rowFormat, exportCallModel.getCallTypeEnum().getNumber(),
                        exportCallModel.getStartTime().format(DatePatternEnum.DEFAULT.getFormatter()),
                        exportCallModel.getEndTime().format(DatePatternEnum.DEFAULT.getFormatter()),
                        exportCallModel.getDuration().format(DatePatternEnum.HH_MM_SS.getFormatter()),
                        df.format(exportCallModel.getCost()))));

        sb.append(lineSeparator);
        var totalFormat = "| %54s| %17s |\n";
        sb.append(String.format(totalFormat, "Total Cost: ", df.format(this.cost) + " rubles"));
        sb.append(lineSeparator);

        return sb.toString();
    }
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

    public TariffTypeEnum getTariffType() {
        return tariffType;
    }

    public void setTariffType(TariffTypeEnum tariffType) {
        this.tariffType = tariffType;
    }

    public List<ExportCallModel> getExportCallModels() {
        return exportCallModels;
    }

    public void setExportCallModels(List<ExportCallModel> exportCallModels) {
        this.exportCallModels = exportCallModels;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
