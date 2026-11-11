package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordleDictionaryLoaderTest {
    @Test
    public void shouldThrowExceptionWhenFileNotFound() {
        assertThrows(WordleDictionaryException.class, () -> {
            new WordleDictionaryLoader(new PrintWriter(System.out)).load("wrongAddress.txt");
        });
    }

    @Test
    public void shouldLoadDictionaryFromFile() {
        List<String> exampleList = List.of("кошка", "мышка", "аббат");
        WordleDictionary wordleDictionary = new WordleDictionaryLoader(new PrintWriter(System.out)).load("test_dictionary.txt");
        assertEquals(exampleList, wordleDictionary.getWords());
        assertTrue(wordleDictionary.contains("кошка"));
        assertFalse(wordleDictionary.contains("ячата"));
        assertEquals(3, wordleDictionary.size());
    }

    @Test
    public void shouldFilterWordsWhenLoadingDictionary() {
        List<String> exampleList = List.of("кошка", "ежики", "аббат", "ячата");
        WordleDictionary wordleDictionary = new WordleDictionaryLoader(new PrintWriter(System.out)).load("filter_test_dictionary.txt");
        assertEquals(exampleList, wordleDictionary.getWords());
        assertTrue(wordleDictionary.contains("кошка"));
        assertFalse(wordleDictionary.contains("ёжики"));
        assertEquals(4, wordleDictionary.size());
    }

    @Test
    public void shouldThrowExceptionWhenFileIsEmpty() {
        assertThrows(WordleDictionaryException.class, () -> {
                    new WordleDictionaryLoader(new PrintWriter(System.out)).load("empty_dictionary.txt");
                }
        );
    }
}
