package ru.yandex.practicum;

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

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        attemptsUsed = 0;
        gameOver = false;
        secretWord = dictionary.getRandomWord();
        this.guesses = new ArrayList<>();
        this.feedback = new ArrayList<>();
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
    /*
    public String getPlayerResponse() {
        return playerResponse;
    }

    public int getAttemptsUsed() {
        return attemptsUsed;
    }

    public WordleDictionary getDictionary() {
        return dictionary;
    }

    public void play() {
        System.out.println("Добро пожаловать в игру WORDLE");
        secretWord = findRandomWord();
        attemptsUsed = 1;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String hint;
            System.out.println("Введите ответ.");
            playerResponse = scanner.nextLine().toLowerCase();
            if (playerResponse.isEmpty()) {
                hint = makeHint();
                System.out.println("Подсказка: " + hint);
                playerResponse = hint;
                continue;
            }

            if (playerResponse.length() != 5) {
                System.out.println("Введен некорректный вариант ответа, в ответе должно быть 5 букв.");
                continue;
            } else if (!checkResponse(playerResponse)) {
                System.out.println("Введен некорректный вариант ответа, в ответе должны быть только буквы.");
                continue;
            } else if (secretWord.equals(playerResponse)) {
                System.out.println("Поздравляем, вы победили!");
                System.out.println("Было загадано слово: " + secretWord);
                break;
            } else if (attemptsUsed == 6) {
                System.out.println("Увы! Количество ходов исчерпано, вы проиграли.");
                System.out.println("Было загадано слово: " + secretWord);
                break;
            }
            System.out.println("Число оставшихся попыток: " + (6 - attemptsUsed));
            attemptsUsed++;
            tip = makeTip();
            System.out.println("Совет: " + tip);
        }

    }


    private boolean checkResponse(String playerResponse) {
        for (Character character : playerResponse.toCharArray()) {
            if (!Character.isLetter(character)) {
                return false;
            }
        }
        return true;
    }

    public String makeHint() {
        if (correctLetters.isEmpty()) {
            return findRandomWord();
        }

        for (String word : dictionary.getWords()) {
            if (checkWord(word)) {
                return word;
            }
        }
        return "hint";
    }

    private boolean checkWord(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (wrongLetters.contains(word.charAt(i))) {
                System.out.println("Проверяем слово " + word);
                System.out.println("Буквы " + word.charAt(i) + " нет в слове");
                return false;
            }
        }
        for (Map.Entry<Character, Integer> entry : correctLetters.entrySet()) {
            if (word.indexOf(entry.getKey()) < 0) {
                return false;
            } else if (entry.getValue() >= 0 && word.charAt(entry.getValue()) != entry.getKey()) {
                return false;
            } else if (entry.getValue() < 0 && word.indexOf(entry.getValue()) == entry.getValue() * -1) {
                return false;
            }
        }
        return true;
    }


    private String makeTip() {
        StringBuilder tip = new StringBuilder();
        for (int i = 0; i < playerResponse.length(); i++) {
            if (!secretWord.contains(Character.toString(playerResponse.charAt(i)))) {
                tip.append('-');
                wrongLetters.add(playerResponse.charAt(i));
            } else if (secretWord.contains(Character.toString(playerResponse.charAt(i))) &&
                    playerResponse.charAt(i) != secretWord.charAt(i)) {
                tip.append('^');
                correctLetters.put(playerResponse.charAt(i), i * -1);
            } else {
                tip.append('+');
                correctLetters.put(playerResponse.charAt(i), i);
            }
        }
        return tip.toString();
    }

    public String findRandomWord() {
        String randomWord = getDictionary().getWords().get(new Random().nextInt(getDictionary().getWords().size()));
        dictionary.getWords().remove(randomWord);
        return randomWord;
    }
    */

