package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.NoAvailableWordsInDictionary;
import ru.yandex.practicum.exceptions.WordNotCorrectByRulesException;
import ru.yandex.practicum.model.WordleDictionary;
import ru.yandex.practicum.model.WordleGame;
import ru.yandex.practicum.printWriter.Loggable;
import ru.yandex.practicum.printWriter.TestPrintWriter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WordleGameTest {
    private WordleDictionary dictionary;
    private Loggable printWriter;
    private WordleGame wordleGame;

    @BeforeEach
    void BeforeEach() {
        dictionary = new WordleDictionary();
        printWriter = new TestPrintWriter();
        wordleGame = new WordleGame(dictionary, printWriter);
    }

    @Test
    void checkForWinning_shouldReturnTrue() {
        boolean result = wordleGame.checkForWinning("ребро", "ребро");
        assertTrue(result);
    }

    @Test
    void checkForWinning_shouldReturnFalse() {
        boolean result = wordleGame.checkForWinning("ребра", "ребро");
        assertFalse(result);
        assertEquals(wordleGame.getUsedWords(), List.of(new String[]{"ребро"}));
    }

    @Test
    void getHint_shouldReturnValueByRegex() throws NoAvailableWordsInDictionary {
        dictionary.addWord("котик");
        dictionary.addWord("домик");
        dictionary.addWord("лесок");

        String hint = wordleGame.getHint("котик");
        assertEquals("котик", hint);
    }

    @Test
    void getHint_shouldReturnErrorByRegexIfDictionaryIsEmpty() {
        assertThrows(NoAvailableWordsInDictionary.class, () -> {
            wordleGame.getHint("котик");
        });
    }

    @Test
    void updatePattern_shouldReturnNewPattern() {
        String pattern = wordleGame.updatePattern("котик", "китик");
        assertEquals("+^+++", pattern);
    }

    @Test
    void getCorrectWord_shouldReturnNormalizedWord() throws NoAvailableWordsInDictionary, WordNotCorrectByRulesException {
        dictionary.addWord("котик");
        String word = wordleGame.getCorrectWord("коТик", 5);
        assertEquals("котик", word);
    }
    @Test
    void getCorrectWord_shouldReturnNoAvailableWordsInDictionary() {
        assertThrows(NoAvailableWordsInDictionary.class, () -> {
            wordleGame.getCorrectWord("коТик", 5);
        });
    }

    @Test
    void getCorrectWord_shouldReturnWordNotCorrectByRulesException(){
        dictionary.addWord("котик");
        assertThrows(WordNotCorrectByRulesException.class, () -> {
            wordleGame.getCorrectWord("коТики", 5);
        });
    }

}
