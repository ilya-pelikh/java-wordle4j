package ru.yandex.practicum.printWriter;

import java.util.ArrayList;
import java.util.List;

public class TestPrintWriter implements Loggable {
    public final List<String> data;

    public TestPrintWriter() {
        data = new ArrayList<>();
    }

    @Override
    public void println(String value) {
        data.add(value);
    }
}
