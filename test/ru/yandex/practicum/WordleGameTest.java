package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordleGameTest {
    WordleGame newGame;

    @BeforeEach
    public void setUp() {
        PrintWriter printWriter = new PrintWriter(System.out);
        newGame = new WordleGame(new WordleDictionary(List.of("кошка"), printWriter), printWriter);
    }

    @Test
    public void shouldStartGameWithZeroAttempts() {
        assertEquals(0, newGame.getAttemptsUsed());
    }

    @Test
    public void shouldStartGameAsNotFinished() {
        assertFalse(newGame.isGameOver());
    }

    @Test
    public void shouldSelectSecretWordFromDictionary() {
        assertEquals("кошка", newGame.getSecretWord());
    }

    @Test
    public void shouldIncreaseAttemptsAfterValidGuess() {
        assertEquals(0, newGame.getAttemptsUsed());
        newGame.makeGuess("кошка");
        assertEquals(1, newGame.getAttemptsUsed());
    }

    @Test
    public void shouldNotIncreaseAttemptsAfterInvalidGuess() {
        assertEquals(0, newGame.getAttemptsUsed());
        try {
            newGame.makeGuess("абвгдё");
        } catch (WordleGameException e){
            System.out.println("Введено неверное слово");
        }
        assertEquals(0, newGame.getAttemptsUsed());
    }
    @Test
    public void shouldRejectWordNotInDictionary() {
        assertThrows(WordleGameException.class, () -> {
                    newGame.makeGuess("сквоб");
        }
        );
    }
    @Test
    public void shouldNormalizeGuessBeforeValidation() {}
    @Test
    public void shouldStoreGuessAndFeedback() {}
}
