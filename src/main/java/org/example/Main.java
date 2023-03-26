package org.example;

import org.example.command.export.ExportCommandImpl;
import org.example.command.parser.impl.ParseCommandImpl;
import org.example.service.impl.ReportServiceImpl;

public class Main {
    public static void main(String[] args) {
        var reportService = new ReportServiceImpl(new ExportCommandImpl(), new ParseCommandImpl());
        reportService.createReportFromFile("cdr.txt");
    }

}