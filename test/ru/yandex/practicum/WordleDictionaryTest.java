package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.WordleDictionaryException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordleDictionaryTest {

    @Test
    public void shouldDeleteRepeatedWords() {
        List<String> testList = List.of("кошка", "кошка", "кошка", "аббат", "аббат");
        List<String> exampleList = List.of("кошка", "аббат");
        WordleDictionary wordleDictionary = new WordleDictionary(testList, new PrintWriter(System.out));
        assertEquals(2, wordleDictionary.size());
        assertEquals(exampleList, wordleDictionary.getWords());
    }

    @Test
    public void shouldNormalizeWords() {
        List<String> testList = List.of("  КОШКА  ", "ЁЖиКи");
        List<String> exampleList = List.of("кошка", "ежики");
        WordleDictionary wordleDictionary = new WordleDictionary(testList, new PrintWriter(System.out));
        assertEquals(exampleList, wordleDictionary.getWords());
    }

    @Test
    public void shouldFilterInvalidWords() {
        List<String> testList = List.of("кот", "robot", "12345", "кошка", "ёжики", "бурдюк");
        List<String> exampleList = List.of("кошка", "ежики");
        WordleDictionary wordleDictionary = new WordleDictionary(testList, new PrintWriter(System.out));
        assertEquals(exampleList, wordleDictionary.getWords());
    }

    @Test
    public void shouldThrowExceptionForEmptyDictionary() {
        List<String> emptyList = new ArrayList<>();
        assertThrows(WordleDictionaryException.class, () -> {
           new WordleDictionary(emptyList, new PrintWriter(System.out));
        });
    }


    @Test
    public void shouldCheckWordPresence() {
        List<String> testList = List.of("кошка", "рост", "правда");
        WordleDictionary wordleDictionary = new WordleDictionary(testList, new PrintWriter(System.out));
        assertTrue(wordleDictionary.contains("кошка"));
        assertFalse(wordleDictionary.contains("рост"));
        assertFalse(wordleDictionary.contains("правда"));
        assertFalse(wordleDictionary.contains("мышка"));
    }
}
