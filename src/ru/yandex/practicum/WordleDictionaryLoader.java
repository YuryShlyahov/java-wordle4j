package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    public WordleDictionary load() {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("words_ru.txt"))) {
            while (br.ready()) {
                String line = br.readLine();
                if (checkWord(line)) {
                    words.add(editWord(line));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new WordleDictionary(words);
    }

    private boolean checkWord(String word) {
        if (word.length() == 5) {
            return true;
        } else {
            return false;
        }
    }

    private String editWord(String word) {
        return word.toLowerCase().replace('ё', 'е');
    }
}
