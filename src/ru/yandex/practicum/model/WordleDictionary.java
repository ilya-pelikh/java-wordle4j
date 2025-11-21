package ru.yandex.practicum.model;
import ru.yandex.practicum.exceptions.NoAvailableHintsException;
import ru.yandex.practicum.exceptions.WordNotCorrectByRulesException;
import ru.yandex.practicum.exceptions.WordNotFoundInDictionaryException;

import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    public static int wordSize = 5;
    public static String falsyPattern;

    private List<String> words;

    public WordleDictionary() {
        words = new ArrayList<>();
        falsyPattern = "-----";
    }

    public String getRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public String normalizeWord(String word) {
        return word.toLowerCase().replace("ё", "e");
    }

    public void addWord(String word) {
        words.add(normalizeWord(word));
    }

    public boolean getIsWordCorrect(String word) throws WordNotCorrectByRulesException, WordNotFoundInDictionaryException {
        boolean hasCorrectSize = word.length() == WordleDictionary.wordSize;

        if (!hasCorrectSize) {
            throw new WordNotCorrectByRulesException(WordleDictionary.wordSize);
        }

        if (!words.contains(word)) {
            throw new WordNotFoundInDictionaryException(word);
        }

        return true;
    }

    public String getPattern(String rawExpectedWord, String rawCurrentWord) {
        String expected = normalizeWord(rawExpectedWord);
        String actual = normalizeWord(rawCurrentWord);

        StringBuilder result = new StringBuilder();

        Set<Character> expectedSet  = new HashSet<>();
        for (char letter : expected.toCharArray()) {
            expectedSet.add(letter);
        }

        char[] actualCharArray = actual.toCharArray();
        for (int i = 0; i < actualCharArray.length; i++) {
            char resultChar = '-';

            char actualChar = actualCharArray[i];

            if (expectedSet.contains(actualChar)) {
                resultChar = '^';
            }

            if (expected.charAt(i) == actual.charAt(i)) {
                resultChar = '+';
            }

            result.append(resultChar);
        }

        return result.toString();
    }

    public boolean checkPatterForWinning(String pattern) {
       return pattern.matches("^\\+{5}$");
    }


    public String getWordForPattern(String rawExpectedWord, String pattern, ArrayList<String> usedWords) throws NoAvailableHintsException {
        String expected = normalizeWord(rawExpectedWord);

        StringBuilder regexForSearch = new StringBuilder();

        char[] patternCharArray = pattern.toCharArray();

        for (int i = 0; i < patternCharArray.length; i++) {
            char charOfPattern = pattern.charAt(i);
            if (charOfPattern == '+') {
                regexForSearch.append(expected.charAt(i));
            } else {
                regexForSearch.append("[А-Яа-я]");
            }
        }

        for (String word : words) {
            if (!usedWords.contains(word) && word.matches(regexForSearch.toString())) {
                if (!word.matches(expected) || checkPatterForWinning(pattern)) {
                    return word;
                }
            }
        }

        throw new NoAvailableHintsException();
    }

    public String getHintForPattern(String rawExpectedWord, String pattern, ArrayList<String> usedWords) throws NoAvailableHintsException {
        char[] patternArray = pattern.toCharArray();

        for (int i = 0; i < patternArray.length; i++) {
            char patternChar = patternArray[i];
            if (patternChar != '+') {
                patternArray[i] = '+';
                break;
            }
        }
        String newPattern = new String(patternArray);

        return getWordForPattern(rawExpectedWord, newPattern, usedWords);
    }

    public List<String> getWords() {
        return words;
    }
}
