package ru.yandex.practicum;

import java.util.Scanner;

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

    private WordleDictionary dictionary;

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
        // String word = getDictionary().getWords().get(new Random().nextInt(getDictionary().getWords().size()));
        guessWord();
        steps = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Число оставшихся попыток: " + (6 - steps));
            System.out.println("Введите ответ.");
            playerResponse = scanner.nextLine();
            steps++;
            if (answer.equals(playerResponse)) {
                System.out.println("Поздравляем, вы победили!");
                break;
            } else if (steps == 6) {
                System.out.println("Количество ходов исчерпано, вы проиграли");
                break;
            }
            System.out.println("Подсказка: " + makeHint());
        }
    }

    private String makeHint() {
        StringBuilder sb = new StringBuilder();
        String hint = "";
        for (int i = 0; i < playerResponse.length(); i++) {
            if (!answer.contains(Character.toString(playerResponse.charAt(i)))) {
                sb.append('-');
            } else if (playerResponse.charAt(i) == answer.charAt(i)) {
                sb.append('+');
            } else {
                sb.append('^');
            }
            hint = sb.toString();
        }
        return hint;
    }
    public void guessWord() {
        answer = "ангел";
    }
}
