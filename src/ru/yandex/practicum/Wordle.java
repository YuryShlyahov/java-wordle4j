package ru.yandex.practicum;

import java.io.IOException;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) throws IOException {
        WordleGame wordleGame = new WordleGame(new WordleDictionaryLoader().load());
        Scanner scanner = new Scanner(System.in);
        while (!wordleGame.isGameOver()) {
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

    }

}
