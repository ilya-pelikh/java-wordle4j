package ru.yandex.practicum.exceptions;

public class DictionaryCouldNotLoad extends Exception {
    public DictionaryCouldNotLoad() {
        super("Ошибка в загрузке словаря");
    }
}
