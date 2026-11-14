package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.WordleDictionaryException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class WordleDictionaryLoader {
    private final PrintWriter logWriter;

    public WordleDictionaryLoader(PrintWriter logWriter) {
        this.logWriter = logWriter;
    }

    public WordleDictionary load(String dictionaryPath) {
        List<String> words = new ArrayList<>();
        if (dictionaryPath.isBlank()) {
            dictionaryPath = "words_ru.txt";
        }
        logWriter.println("Начата загрузка файла словаря " + dictionaryPath);
        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryPath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            logWriter.println("Ошибка чтения файла словаря: " + e.getMessage());
            throw new WordleDictionaryException("Ошибка чтения файла словаря", e);
        }
        logWriter.println("Чтение файла завершено, прочитано строк: " + words.size());
        return new WordleDictionary(words, logWriter);
    }
}


