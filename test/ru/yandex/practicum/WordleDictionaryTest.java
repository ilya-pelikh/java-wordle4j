package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.NoAvailableHintsException;
import ru.yandex.practicum.exceptions.WordNotCorrectByRulesException;
import ru.yandex.practicum.exceptions.WordNotFoundInDictionaryException;
import ru.yandex.practicum.model.WordleDictionary;

import java.util.ArrayList;

public class WordleDictionaryTest {
    public static String[] dict = {"слава", "сяяяя", "сляяя", "слаяя", "славя"};
    public static WordleDictionary wordleDictionary;

    @BeforeAll
    public static void BeforeAll() {
        wordleDictionary = new WordleDictionary();

        for (String word : dict) {
            wordleDictionary.addWord(word);
        }
    }

    @Test
    @DisplayName("Проверяем нормализацию и добавление")
    public void testAddingWordsToDictionary() {
        Assertions.assertEquals(dict.length, wordleDictionary.getWords().size());

       for(String word: wordleDictionary.getWords()) {
           Assertions.assertTrue(wordleDictionary.getWords().contains(word));
       }
    }

    @Test
    @DisplayName("Проверяем получение случайного слова")
    public void testGettingRandomWord() {
        String randomWord = wordleDictionary.getRandomWord();
        Assertions.assertTrue(wordleDictionary.getWords().contains(randomWord));
    }

    @Test
    @DisplayName("Проверяем слово на корректность")
    void testIsWordCorrect() {
        Assertions.assertDoesNotThrow(() -> wordleDictionary.getIsWordCorrect("слава"));

        Assertions.assertThrows(WordNotFoundInDictionaryException.class,
                () -> wordleDictionary.getIsWordCorrect("абвгд"));

        Assertions.assertThrows(WordNotCorrectByRulesException.class,
                () -> wordleDictionary.getIsWordCorrect("программирование"));
    }

    @Test
    @DisplayName("Получаем паттерны")
    void testGettingPattern() {
        Assertions.assertEquals("+++++", wordleDictionary.getPattern("слава", "слава"));
        Assertions.assertEquals("-----", wordleDictionary.getPattern("слава", "яяяяя"));
        Assertions.assertEquals("^^^^^", wordleDictionary.getPattern("слава", "лсссс"));
    }

    @Test
    @DisplayName("Проверяем паттерн на успех")
    void testValidatingSuccessPattern() {
        Assertions.assertTrue(wordleDictionary.checkPatterForWinning("+++++"));
    }

    @Test
    @DisplayName("Получаем слово по паттерну")
    void testGettingWordForPattern() {
        String input = dict[0];
        try {
            String word = wordleDictionary.getWordForPattern(input, "+----", new ArrayList<>());
            Assertions.assertEquals(dict[1], word);
        } catch (NoAvailableHintsException exception) {
            throw new RuntimeException();
        }
    }

    @Test
    @DisplayName("Получаем слово по подсказке и паттерну")
    void testGettingHintWordForPattern() {
        String input = dict[0];
        try {
            String word = wordleDictionary.getHintForPattern(input, "+----", new ArrayList<>());
            Assertions.assertEquals(dict[2], word);
        } catch (NoAvailableHintsException exception) {
            throw new RuntimeException();
        }
    }

}
