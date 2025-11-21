package ru.yandex.practicum.exceptions;

public class NoAvailableHintsException extends Exception {
    public NoAvailableHintsException() {
        super("Подсказки закончились");
    }
}