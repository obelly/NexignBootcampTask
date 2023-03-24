package org.example;

import org.example.kit.Parser;
import org.example.kit.Printer;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        Printer printer = new Printer();
        printer.print(parser.parse());
    }
}