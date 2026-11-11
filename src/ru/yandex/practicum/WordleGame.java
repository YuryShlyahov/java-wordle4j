package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */

public class WordleGame {

    private static final int MAX_ATTEMPTS = 6;
    private final String secretWord;
    private int attemptsUsed;
    private boolean gameOver;
    private final WordleDictionary dictionary;
    private final List<String> guesses;
    private final List<String> feedback;
    private final PrintWriter logWriter;

    public WordleGame(WordleDictionary dictionary, PrintWriter logWriter) {
        this.logWriter = logWriter;
        this.dictionary = dictionary;
        attemptsUsed = 0;
        gameOver = false;
        secretWord = dictionary.getRandomWord();
        this.guesses = new ArrayList<>();
        this.feedback = new ArrayList<>();
        logWriter.println("Игра начата");
    }

    public String makeGuess(String word) {
        if (gameOver) {
            throw new WordleGameException("Игра уже завершена");
        }
        word = dictionary.editWord(word);
        if (dictionary.checkWord(word) && dictionary.contains(word)) {
            increaseAttemptsUsed();
            String tip = evaluateGuess(secretWord, word);
            guesses.add(word);
            feedback.add(tip);
            if (tip.equals("+++++") || attemptsUsed >= MAX_ATTEMPTS) {
                setGameOver(true);
            }
            return tip;
        } else {
            throw new WordleGameException("Недопустимое слово");
        }
    }

    private String evaluateGuess(String secret, String word) {
        int length = secret.length();
        char[] tip = new char[length];
        boolean[] secretUsed = new boolean[length];
        for (int i = 0; i < length; i++) {
            if (word.charAt(i) == secret.charAt(i)) {
                tip[i] = '+';
                secretUsed[i] = true;
            }
        }
        for (int i = 0; i < length; i++) {
            if (tip[i] == '+') {
                continue;
            }
            char currentChar = word.charAt(i);
            boolean found = false;
            for (int j = 0; j < length; j++) {
                if (!secretUsed[j] && secret.charAt(j) == currentChar) {
                    tip[i] = '^';
                    secretUsed[j] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                tip[i] = '-';
            }
        }
        return new String(tip);
    }

    public String suggestWord() {
        for (String candidate : dictionary.getWords()) {
            boolean valid = true;
            if (guesses.contains(candidate)) {
                continue;
            }
            for (int i = 0; i < guesses.size(); i++) {
                String previousGuess = guesses.get(i);
                String previousTip = feedback.get(i);
                if (!evaluateGuess(candidate, previousGuess).equals(previousTip)) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                return candidate;
            }
        }
        return null;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public int getAttemptsUsed() {
        return attemptsUsed;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public WordleDictionary getDictionary() {
        return dictionary;
    }

    private void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    private void increaseAttemptsUsed() {
        attemptsUsed++;
    }

    public List<String> getGuesses() {
        return List.copyOf(guesses);
    }

    public List<String> getFeedback() {
        return List.copyOf(feedback);
    }
}
