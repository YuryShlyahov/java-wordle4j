package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    public WordleDictionary(List<String> words) throws IllegalStateException {
        this.words = new ArrayList<>();
        for (String word : words) {
            add(word);
        }
        if (this.words.isEmpty()) {
            throw new IllegalStateException("В словаре нет подходящих слов");
        }
    }

    private final List<String> words;

    public List<String> getWords() {
        return List.copyOf(words);
    }

    public String getRandomWord() {
        return words.get(new Random().nextInt(words.size()));
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

