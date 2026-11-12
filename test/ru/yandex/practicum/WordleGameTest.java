package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordleGameTest {
    WordleGame newGame;
    PrintWriter printWriter;

    @BeforeEach
    public void setUp() {
        printWriter = new PrintWriter(System.out);
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
        } catch (WordleGameException e) {
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
    public void shouldNormalizeGuessBeforeValidation() {
        String test = " ЁЖиКи  ";
        newGame = new WordleGame(new WordleDictionary(List.of("ёжики"), printWriter), printWriter);
        assertEquals("+++++", newGame.makeGuess(test));
    }

    @Test
    public void shouldStoreGuessAndFeedback() {
        newGame.makeGuess("кошка");
        List<String> feedBackTestList = List.of("+++++");
        List<String> guessesTestList = List.of("кошка");
        assertEquals(feedBackTestList, newGame.getFeedback());
        assertEquals(guessesTestList, newGame.getGuesses());
    }

    @Test
    public void shouldMarkCorrectLettersWithPlus() {
        assertEquals("+++++", newGame.makeGuess("кошка"));
    }

    @Test
    public void shouldMarkMisplacedLettersWithCaret() {
        newGame = new WordleGame(new WordleDictionary(List.of("драка", "карат"), printWriter), printWriter, "карат");
        assertEquals("-^^^^", newGame.makeGuess("драка"));
    }

    @Test
    public void shouldMarkAbsentLettersWithMinus() {
        newGame = new WordleGame(new WordleDictionary(List.of("аббат", "рывок"), printWriter), printWriter, "рывок");
        assertEquals("-----", newGame.makeGuess("аббат"));
    }

    @Test
    public void shouldHandleRepeatedLettersCorrectly() {
        newGame = new WordleGame(new WordleDictionary(List.of("аббат", "батон"), printWriter), printWriter, "батон");
        assertEquals("^^--^", newGame.makeGuess("аббат"));
    }

    @Test
    public void shouldFinishGameAfterCorrectGuess() {
        assertFalse(newGame.isGameOver());
        newGame.makeGuess("кошка");
        assertTrue(newGame.isGameOver());
    }

    @Test
    public void shouldFinishGameAfterSixIncorrectGuesses() {
        newGame = new WordleGame(new WordleDictionary(List.of("аббат", "батон", "бурка", "топор", "вилка", "ложка", "ячата"), printWriter), printWriter, "батон");
        assertFalse(newGame.isGameOver());
        newGame.makeGuess("аббат");
        newGame.makeGuess("бурка");
        newGame.makeGuess("топор");
        newGame.makeGuess("вилка");
        newGame.makeGuess("ложка");
        newGame.makeGuess("ячата");
        assertTrue(newGame.isGameOver());
    }

    @Test
    public void shouldRejectGuessAfterGameIsFinished() {
        newGame = new WordleGame(new WordleDictionary(List.of("аббат", "батон", "бурка", "топор", "вилка", "ложка", "ячата", "ропот"), printWriter), printWriter, "батон");
        assertFalse(newGame.isGameOver());
        newGame.makeGuess("аббат");
        newGame.makeGuess("бурка");
        newGame.makeGuess("топор");
        newGame.makeGuess("вилка");
        newGame.makeGuess("ложка");
        newGame.makeGuess("ячата");
        assertThrows(WordleGameException.class, () -> {
                    newGame.makeGuess("ропот");
                }
        );
    }

    @Test
    public void shouldSuggestWordFromDictionary() {
        assertEquals("кошка", newGame.suggestWord());
    }

    @Test
    public void shouldNotSuggestPreviouslyGuessedWord() {
        newGame = new WordleGame(new WordleDictionary(List.of( "кошка"), printWriter), printWriter, "аббат");
        newGame.makeGuess("кошка");
        assertNull(newGame.suggestWord());
    }

    @Test
    public void shouldReturnNullWhenNoCandidatesRemain() {
        newGame = new WordleGame(new WordleDictionary(List.of("аббат", "кошка"), printWriter), printWriter, "кошки");
        newGame.makeGuess("кошка");
        assertNull(newGame.suggestWord());
    }
}
