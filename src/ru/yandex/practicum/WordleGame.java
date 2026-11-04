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

    private void play(WordleGame game){
        String word = game.getDictionary().getWords().get(new Random().nextInt(game.getDictionary().getWords().size()));
        steps = 0;
        Scanner scanner = new Scanner(System.in);
        String message = "AAAAA";
        while (!word.equals(answer)){
            answer = scanner.nextLine();
            steps++;
            for (int i = 0; i < answer.length(); i++) {
                if(answer.charAt(i) == word.charAt(i)){
                    message = message.replace(message.charAt(i), '+');
                }
            }
            if(steps == 6){
                break;
            }
        }

    }
}
