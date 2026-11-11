package ru.yandex.practicum;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter logWriter = new PrintWriter("wordleLog.log")) {
            try {
                logWriter.println("Программа начала работу");
                WordleGame wordleGame;
                System.out.println("Введите имя файла словаря. Пустой ввод - словарь по умолчанию.");
                Scanner scanner = new Scanner(System.in);
                String dictionaryPath = scanner.nextLine();
                try {
                    wordleGame = new WordleGame(new WordleDictionaryLoader(logWriter).load(dictionaryPath), logWriter);
                } catch (WordleDictionaryException e) {
                    System.out.println("Ошибка загрузки словаря");
                    logWriter.println("Ошибка загрузки словаря: " + e.g)etMessage());
                    e.printStackTrace(logWriter);
                    return;
                }
                while (!wordleGame.isGameOver()) {
                    System.out.println("Введите ваш вариант ответа");
                    String userInput = scanner.nextLine();

                    if (userInput.isBlank()) {
                        userInput = wordleGame.suggestWord();
                    }
                    if (userInput == null) {
                        System.out.println("Подходящих слов не найдено");
                        break;
                    }
                    System.out.println("Введен ответ: " + userInput);
                    try {
                        String tip = wordleGame.makeGuess(userInput);
                        System.out.println(tip);
                        if (wordleGame.isGameOver()) {
                            if (tip.equals("+++++")) {
                                System.out.println("Поздравляю, вы победили!");
                            } else {
                                System.out.println("Увы, вы проиграли!");
                                System.out.println("Было загадано слово " + wordleGame.getSecretWord());
                            }
                        }
                    } catch (WordleGameException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println("Произошла ошибка, работа программы завершена");
                logWriter.println("Возникла непредвиденная ошибка в работе программы");
                e.printStackTrace(logWriter);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка, программа не смогла создать лог-файл, работа программы завершена");
        }
    }
}
