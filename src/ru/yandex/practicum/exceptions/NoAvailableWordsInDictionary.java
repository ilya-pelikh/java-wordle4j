package ru.yandex.practicum.exceptions;

public class NoAvailableWordsInDictionary extends Exception {
    public NoAvailableWordsInDictionary() {
        super("Доступное слово не найдено в словаре");
    }
}