package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.exceptions.DictionaryIsEmpty;
import ru.yandex.practicum.exceptions.NoAvailableWordsInDictionary;
import ru.yandex.practicum.model.WordleDictionary;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {
    private WordleDictionary dictionary;

    @BeforeEach
    void BeforeEach() {
        dictionary = new WordleDictionary();
    }

    @Test
    void normalizeWord_shouldConvertToLowerCaseAndReplace() {
        assertEquals("привет", dictionary.normalizeWord("ПРИВЕТ"));
        assertEquals("тест", dictionary.normalizeWord("ТЕСТ"));
    }

    @Test
    void addWord_shouldNormalizeAndStoreWord() {
        dictionary.addWord("Привет");
        dictionary.addWord("доЖдь");
        dictionary.addWord("Тест");

        List<String> expected = List.of("привет", "дождь", "тест");
        assertTrue(dictionary.getWords().containsAll(expected));
    }

    @Test
    void getRandomWord_shouldReturnWordFromDictionary() {
        dictionary.addWord("кот");
        dictionary.addWord("дом");
        dictionary.addWord("лес");

        String randomWord = dictionary.getRandomWord();
        assertTrue(List.of("кот", "дом", "лес").contains(randomWord));
    }

    @Test
    void getRandomWord_shouldThrowExceptionWhenDictionaryIsEmpty() {
        assertThrows(DictionaryIsEmpty.class, () -> {
            dictionary.getRandomWord();
        });
    }

    @Test
    void getWordByRegex_shouldReturnFirstMatchingWordNotInExcluded() throws NoAvailableWordsInDictionary {
        dictionary.addWord("кот");
        dictionary.addWord("код");
        dictionary.addWord("кол");

        ArrayList<String> excluded = new ArrayList<>();
        excluded.add("кот");

        String result = dictionary.getWordByRegex("к.д", excluded);
        assertEquals("код", result);
    }

    @Test
    void getWordByRegex_shouldIgnoreExcludedWords() throws NoAvailableWordsInDictionary {
        dictionary.addWord("кот");
        dictionary.addWord("код");

        ArrayList<String> excluded = new ArrayList<>();
        excluded.add("код");

        String result = dictionary.getWordByRegex("к..", excluded);
        assertEquals("кот", result);
    }

    @Test
    void getWordByRegex_shouldMatchSimpleRegex() throws NoAvailableWordsInDictionary {
        dictionary.addWord("мама");
        dictionary.addWord("рама");
        dictionary.addWord("лама");

        ArrayList<String> excluded = new ArrayList<>();

        String result = dictionary.getWordByRegex("м..а", excluded);
        assertEquals("мама", result);
    }

    @Test
    void getWordByRegex_shouldHandleEmptyDictionary() {
        ArrayList<String> excluded = new ArrayList<>();

        assertThrows(NoAvailableWordsInDictionary.class, () -> {
            dictionary.getWordByRegex(".*", excluded);
        });
    }
}
