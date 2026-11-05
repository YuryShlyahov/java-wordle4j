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

    private String playerResponse;
    private String answer;
    private int steps;
    private String tip;

    private WordleDictionary dictionary;

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
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
        guessWord();
        steps = 1;
        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("Введите ответ.");
            playerResponse = scanner.nextLine().toLowerCase();
            if (playerResponse.isEmpty() && tip != null){
                System.out.println("Подсказка: " + makeHint() );
            } else if (playerResponse.length() != 5) {
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

    public String makeHint(){
        String hint = "";
        for (String word : dictionary.getWords()){
            for (int i = 0; i < word.length(); i++) {
                if(tip.charAt(i) == '+'){
                    if(word.charAt(i) != playerResponse.charAt(i)){
                        continue;
                    } else {
                        hint = word;
                    }
                }
            }
        }
        return hint;
    }

    private String makeTip() {
        StringBuilder tip = new StringBuilder();
        for (int i = 0; i < playerResponse.length(); i++) {
            if (!answer.contains(Character.toString(playerResponse.charAt(i)))) {
                tip.append('-');
            } else if (playerResponse.charAt(i) == answer.charAt(i)) {
                tip.append('+');
            } else {
                tip.append('^');
            }
        }
        return tip.toString();
    }

    public void guessWord() {
        answer = getDictionary().getWords().get(new Random().nextInt(getDictionary().getWords().size()));
        System.out.println("Я загадал слово из " + answer.length() + " букв");
    }
}
