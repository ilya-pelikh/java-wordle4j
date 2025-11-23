package ru.yandex.practicum.exceptions;

public class WordNotCorrectByRulesException extends Exception {
    public WordNotCorrectByRulesException(int count) {
        super("Длина слова должна быть " + count + " символов");
    }
}