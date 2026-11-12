package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.WordleDictionaryException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WordleDictionary {
    private final PrintWriter logWriter;
    private Random random;
    private final List<String> words;

    public WordleDictionary(List<String> words, PrintWriter logWriter) {
        random = new Random();
        this.words = new ArrayList<>();
        this.logWriter = logWriter;
        for (String word : words) {
            add(word);
        }
        if (this.words.isEmpty()) {
            throw new WordleDictionaryException("В словаре нет подходящих слов");
        }
        logWriter.println("Создание игрового словаря завершено, подходящих слов: " + this.words.size());
    }



    public List<String> getWords() {
        return List.copyOf(words);
    }

    public String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public int size() {
        return words.size();
    }

    public void add(String word) {
        word = editWord(word);
        if (checkWord(word) && !contains(word)) {
            words.add(word);
        }
    }

    public boolean checkWord(String word) {
        return word.matches("[а-я]{5}");
    }

    public String editWord(String word) {
        word = word.trim();
        word = word.toLowerCase();
        word = word.replace('ё', 'е');
        return word;
    }
}

