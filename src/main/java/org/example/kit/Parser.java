package org.example.kit;

import org.example.entity.Call;
import org.example.entity.CallType;
import org.example.entity.Client;
import org.example.entity.TariffType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser {
    /**
     * Map, со всеми рассчитанными параметрами
     * @return
     */
    public Map<Client, List<Call>> parse() {
        /**
         * читаю файл
         */
        Reader reader = new Reader();
        List<String[]> list = reader.read("cdrTest.txt");
        Map<Client, List<Call>> map = new HashMap<>();
        /**
         * из файла создаю map -> Client, List<Call>
         * тут же вызываю статический метод calculate для рассчета стоимости
         */
        List<Call> calls = new ArrayList<>();
        for (String[] str : list) {

            Call call = new Call(CallType.withNumber(str[0]), Long.valueOf(str[1]), convertInDateTime(str[2]),
                    convertInDateTime(str[3]), TariffType.withNumber(str[4]));
            calls.add(call);
            map = calls.stream()
                    .collect(Collectors.groupingBy(
                            c -> new Client(c.getPhoneNumber(), c.getTariffType())
                    ));
        }
        for (Map.Entry<Client, List<Call>> entry : map.entrySet()) {
            Long totalTime = entry.getValue().stream()
                    .mapToLong(Call::durationToMinutes)
                    .reduce(0, Long::sum);

            Long totalTimeForRegular = entry.getValue().stream()
                    .filter(e -> e.getCallType() == CallType.OUTGOING)
                    .mapToLong(Call::durationToMinutes)
                    .reduce(0, Long::sum);

            for (Call call : entry.getValue()) {
                if (call.getTariffType() == TariffType.REGULAR) {
                    call.setCost(Calculator.calculate(
                            call.getTariffType(),
                            call.getDuration(),
                            call.getCallType(),
                            totalTimeForRegular));
                } else {
                    call.setCost(Calculator.calculate(
                            call.getTariffType(),
                            call.getDuration(),
                            call.getCallType(),
                            totalTime));
                }
                if (call.getCallType() == CallType.OUTGOING) {
                    System.out.println(call.durationToMinutes());
                }
            }
            Double totalCost = entry.getValue().stream()
                    .mapToDouble(Call::getCost)
                    .reduce(0, Double::sum);
            System.out.println(totalCost);
            System.out.println(totalTimeForRegular);
        }
        return map;
    }

    private LocalDateTime convertInDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.parse(dateString, formatter);
    }
}
