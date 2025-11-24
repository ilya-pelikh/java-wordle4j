package ru.yandex.practicum.printWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

public class PrintWriter implements Loggable {
    private final String filePath;

    public PrintWriter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void println(String action) {
        try (Writer writer = new FileWriter(filePath, true)) {
            String text = new Date() + "  " + action + "\n";
            writer.write(text);
        } catch (IOException err) {
            System.err.println("Ошибка записи в лог: " + err.getMessage());
        }
    }
}
