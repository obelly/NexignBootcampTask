package org.example.kit;

import org.example.entity.Call;
import org.example.entity.Client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.List;
import java.util.Map;

public class Printer {


    public void print(Map<Client, List<Call>> clientListMap) {

        for (Map.Entry<Client, List<Call>> entry : clientListMap.entrySet()) {
            Formatter f = new Formatter();
            String s = entry.getKey().getPhoneNumber() + ".txt";
            File file = Path.of("reports", s).toFile();
            String tariff = "\nTariff index: " + entry.getKey().getTariffType().getNumber();
            String tire = "\n--------------------------------------------------------------------------";
            String report = "\nReport for phone number " + entry.getKey().getPhoneNumber() + ": ";
            String str = f.format("|%n%10.10s|%20.20s|%20.20s|%10.10s|%10.10s|",
                    "Call Type", "Start Type", "End Type", "Duration", "Cost").toString();

            StringBuilder header = new StringBuilder(tariff + tire + report + tire + str + tire);
            f.close();
            StringBuilder sb = new StringBuilder();

            for (Call call : entry.getValue()) {
                sb.append(call.toString());
                sb.append("\n");
            }
            sb.append("\n");

            try (FileWriter fileWriter = new FileWriter(file)){
                fileWriter.append(header);
                fileWriter.append(sb);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            System.out.println(header+ " " + sb);

        }
    }
}
