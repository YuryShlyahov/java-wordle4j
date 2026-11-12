package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

public class WordleGame {

    public static final int MAX_ATTEMPTS = 6;
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
    public WordleGame(WordleDictionary dictionary, PrintWriter logWriter, String secretWord) {
        this.logWriter = logWriter;
        this.dictionary = dictionary;
        attemptsUsed = 0;
        gameOver = false;
        this.secretWord = dictionary.editWord(secretWord);
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
            logWriter.println("Попытка: " + word + ", результат: " + tip);
            logWriter.println("Использовано попыток: " + attemptsUsed + " из " + MAX_ATTEMPTS);
            guesses.add(word);
            feedback.add(tip);
            if (tip.equals("+++++") || attemptsUsed >= MAX_ATTEMPTS) {
                setGameOver(true);
                if(tip.equals("+++++")){
                    logWriter.println("Игра завершилась победой");
                } else {
                    logWriter.println("Игра завершилась поражением");
                }
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
                logWriter.println("Компьютер предложил слово: " + candidate);
                return candidate;
            }
        }
        logWriter.println("Подходящие слова не найдены в словаре");
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
