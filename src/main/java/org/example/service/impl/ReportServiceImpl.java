package org.example.service.impl;

import org.example.command.export.ExportCommand;
import org.example.command.parser.ParseCommand;
import org.example.enums.CallTypeEnum;
import org.example.enums.TariffTypeEnum;
import org.example.model.CallDataRecord;
import org.example.model.ExportCallDataModel;
import org.example.model.ExportCallModel;
import org.example.service.ReportService;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {

    public static final double ZERO = 0.0;
    public static final int FIRST_MINUTES_REGULAR_TARIFF = 100;
    public static final int FIRST_MINUTES_UNLIMITED_TARIFF = 300;
    public static final int PRICE_UNLIMITED = 100;
    private static final double SECONDS_IN_MINUTE = 60.0;
    private final ExportCommand<ExportCallDataModel> exportCommand;
    private final ParseCommand<CallDataRecord> parseCommand;

    public ReportServiceImpl(ExportCommand<ExportCallDataModel> exportCommand, ParseCommand<CallDataRecord> parseCommand) {
        this.exportCommand = exportCommand;
        this.parseCommand = parseCommand;
    }

    @Override
    public void createReportFromFile(String file) {
        var callDataRecordsList = parseCommand.process(file);
        var mapCallDataRecords = callDataRecordsList.stream()
                .collect(Collectors.groupingBy(CallDataRecord::getPhoneNumber));
        mapCallDataRecords.forEach((phoneNumber, callDataRecords) -> {
            var exportCallDataModel = new ExportCallDataModel();
            exportCallDataModel.setPhoneNumber(phoneNumber);
            var tariffType = callDataRecords.stream().findFirst()
                    .orElseThrow(() -> new RuntimeException(String.format("Список с тарифом пустой для номера %s", phoneNumber)))
                    .getTariffType();
            exportCallDataModel.setTariffType(tariffType);
            var exportCallModelList = createExportCallModelList(callDataRecords);
            exportCallDataModel.setExportCallModels(exportCallModelList);
            var sumCost = exportCallModelList.stream().mapToDouble(ExportCallModel::getCost).sum();

            exportCallDataModel.setCost(getTotalCostByTariff(sumCost, exportCallDataModel.getTariffType()));
            exportCommand.process(exportCallDataModel);
        });
    }

    private List<ExportCallModel> createExportCallModelList(List<CallDataRecord> callDataRecordList) {
        int countUnlimited = 0;
        int countStandard = 0;
        List<ExportCallModel> exportCallModels = new ArrayList<>();
        for (var callDataRecord : callDataRecordList) {
            var exportCallModel = new ExportCallModel();
            exportCallModel.setCallTypeEnum(callDataRecord.getCallType());
            exportCallModel.setStartTime(callDataRecord.getStartTime());
            exportCallModel.setEndTime(callDataRecord.getEndTime());
            var duration = LocalTime.ofSecondOfDay(Duration.between(callDataRecord.getStartTime(), callDataRecord.getEndTime()).getSeconds());
            var totalMinutes = (int) Math.ceil(duration.toSecondOfDay() / SECONDS_IN_MINUTE);
            exportCallModel.setDuration(duration);

            if (CallTypeEnum.OUTGOING.equals(callDataRecord.getCallType())) {
                switch (callDataRecord.getTariffType()) {
                    case PER_MINUTE:
                        exportCallModel.setCost(totalMinutes * TariffTypeEnum.PER_MINUTE.getPriceRubMin());
                        break;
                    case REGULAR:
                        countStandard += totalMinutes;
                        var remainingMinutesRegular = countStandard - FIRST_MINUTES_REGULAR_TARIFF;
                        if (remainingMinutesRegular > ZERO) {
                            var regularTariffMinutes = Math.min(totalMinutes, remainingMinutesRegular);
                            var perMinuteMinutes = totalMinutes - regularTariffMinutes;
                            exportCallModel.setCost(regularTariffMinutes * TariffTypeEnum.REGULAR.getPriceRubMin() + perMinuteMinutes * TariffTypeEnum.PER_MINUTE.getPriceRubMin());
                        } else {
                            exportCallModel.setCost(totalMinutes * TariffTypeEnum.REGULAR.getPriceRubMin());
                        }
                        break;
                    case UNLIMITED:
                        countUnlimited += totalMinutes;
                        if (countUnlimited >= FIRST_MINUTES_UNLIMITED_TARIFF) {
                            var remainingMinutesUnlimited = countUnlimited - FIRST_MINUTES_UNLIMITED_TARIFF;
                            if (remainingMinutesUnlimited > ZERO) {
                                var perMinuteMinutes = totalMinutes - Math.min(totalMinutes, remainingMinutesUnlimited);
                                exportCallModel.setCost(perMinuteMinutes * TariffTypeEnum.UNLIMITED.getPriceRubMin());
                            } else {
                                exportCallModel.setCost(totalMinutes * TariffTypeEnum.UNLIMITED.getPriceRubMin());
                            }
                        } else {
                            exportCallModel.setCost(ZERO);
                        }
                        break;
                    default:
                        throw new RuntimeException(String.format("Неизвестный тариф: %s", callDataRecord.getTariffType()));
                }
            } else {
                exportCallModel.setCost(ZERO);

            }
            exportCallModels.add(exportCallModel);
        }
        return exportCallModels.stream()
                .sorted(Comparator.comparing(ExportCallModel::getCallTypeEnum)
                        .thenComparing(ExportCallModel::getStartTime))
                .collect(Collectors.toList());
    }

    private double getTotalCostByTariff(double sumCost, TariffTypeEnum tariff) {
        switch (tariff) {
            case PER_MINUTE:
            case REGULAR:
                return sumCost;
            case UNLIMITED:
                return sumCost > 0 ? sumCost + PRICE_UNLIMITED : PRICE_UNLIMITED;
            default:
                throw new RuntimeException(String.format("Неизвестный тариф: %s", tariff));
        }
    }
}
