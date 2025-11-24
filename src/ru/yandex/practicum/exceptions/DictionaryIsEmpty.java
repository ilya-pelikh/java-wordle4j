package ru.yandex.practicum.exceptions;

public class DictionaryIsEmpty extends RuntimeException {
    public DictionaryIsEmpty() {
        super("Словарь пуст");
    }
}
