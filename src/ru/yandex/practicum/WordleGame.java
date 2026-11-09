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

    private String playerResponse;
    private String answer;
    private int steps;
    private String tip;
    private Set<Character> wrongLetters;
    private Map<Character, Integer> correctLetters;
    private WordleDictionary dictionary;

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        wrongLetters = new HashSet<>();
        correctLetters = new HashMap<>();
    }

    public String getPlayerResponse() {
        return playerResponse;
    }

    public int getSteps() {
        return steps;
    }

    public WordleDictionary getDictionary() {
        return dictionary;
    }

    public void play() {
        System.out.println("Добро пожаловать в игру WORDLE");
        answer = findRandomWord();
        steps = 1;
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
            } else if (answer.equals(playerResponse)) {
                System.out.println("Поздравляем, вы победили!");
                System.out.println("Было загадано слово: " + answer);
                break;
            } else if (steps == 6) {
                System.out.println("Увы! Количество ходов исчерпано, вы проиграли.");
                System.out.println("Было загадано слово: " + answer);
                break;
            }
            System.out.println("Число оставшихся попыток: " + (6 - steps));
            steps++;
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
            if (!answer.contains(Character.toString(playerResponse.charAt(i)))) {
                tip.append('-');
                wrongLetters.add(playerResponse.charAt(i));
            } else if (answer.contains(Character.toString(playerResponse.charAt(i))) &&
                    playerResponse.charAt(i) != answer.charAt(i)) {
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
}
