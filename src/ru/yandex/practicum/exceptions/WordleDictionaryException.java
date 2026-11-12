package ru.yandex.practicum.exceptions;

public class WordleDictionaryException extends RuntimeException {
    public WordleDictionaryException(String message) {
        super(message);
    }
    public WordleDictionaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
