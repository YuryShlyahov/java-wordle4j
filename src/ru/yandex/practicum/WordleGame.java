package ru.yandex.practicum;

import java.util.Random;
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

    private String answer;

    private int steps;

    private WordleDictionary dictionary;

    public String getAnswer() {
        return answer;
    }

    public int getSteps() {
        return steps;
    }

    public WordleDictionary getDictionary() {
        return dictionary;
    }

    public void play() {
        // String word = getDictionary().getWords().get(new Random().nextInt(getDictionary().getWords().size()));
        String word = "ангел";
        steps = 0;
        Scanner scanner = new Scanner(System.in);
        String message = "AAAAA";
        StringBuilder sb = new StringBuilder(message);
        while (!word.equals(answer)) {
            System.out.println("Число оставшихся попыток: " + (6 - steps));
            System.out.println("Введите ответ.");
            answer = scanner.nextLine();
            steps++;
            for (int i = 0; i < answer.length(); i++) {
                if (word.contains(Character.toString(answer.charAt(i)))) {
                    sb.setCharAt(i, '^');
                }
                if (answer.charAt(i) == word.charAt(i)) {
                    sb.setCharAt(i, '+');

                }
                message = sb.toString();
                System.out.println("виток цикла " + i + " Сообщение " + message);
            }
            System.out.println("Подсказка: " + message);
            if (steps == 6) {
                break;
            }
        }

    }
}
