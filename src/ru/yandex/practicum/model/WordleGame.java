package ru.yandex.practicum.model;

import ru.yandex.practicum.exceptions.NoAvailableWordsInDictionary;
import ru.yandex.practicum.exceptions.WordNotCorrectByRulesException;
import ru.yandex.practicum.printWriter.Loggable;

import java.util.*;

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
    private final WordleDictionary dictionary;
    private final Loggable printWriter;

    public ArrayList<String> getUsedWords() {
        return usedWords;
    }

    private final ArrayList<String> usedWords;

    private String pattern;

    public WordleGame(WordleDictionary dictionary, Loggable printWriter) {
        this.pattern = "-----";
        this.usedWords = new ArrayList<>();
        this.printWriter = printWriter;
        this.dictionary = dictionary;
    }

    public boolean checkForWinning(String answer, String word) {
        if (word.equals(answer)) {
            return true;
        }
        usedWords.add(word);
        return false;
    }

    public String getHint(String answer) throws NoAvailableWordsInDictionary {
        try {
            StringBuilder regexForSearch = new StringBuilder();

            char[] patternCharArray = pattern.toCharArray();

            for (int i = 0; i < patternCharArray.length; i++) {
                char charOfPattern = pattern.charAt(i);
                if (charOfPattern == '+') {
                    regexForSearch.append(answer.charAt(i));
                } else {
                    regexForSearch.append("[А-Яа-я]");
                }
            }

            String foundWord =  dictionary.getWordByRegex(regexForSearch.toString(), usedWords);
            this.usedWords.add(foundWord);
            return foundWord;
        } catch (NoAvailableWordsInDictionary exception) {
            printWriter.println(exception.getMessage());
            throw exception;
        }
    }

    public String getCorrectWord(String word, int wordLength) throws WordNotCorrectByRulesException, NoAvailableWordsInDictionary {
        boolean hasCorrectSize = word.length() == wordLength;

        if (!hasCorrectSize) {
            throw new WordNotCorrectByRulesException(wordLength);
        }

        try {
            return dictionary.getWordByRegex(dictionary.normalizeWord(word), new ArrayList<>());
        } catch (NoAvailableWordsInDictionary exception) {
            printWriter.println(exception.getMessage());
            throw exception;
        }
    }

    public String updatePattern(String answer, String word) {
        String cleanPattern = calculateCleanPattern(answer, word);

        updateGamePattern(answer, word);

        return cleanPattern;
    }


    private void updateGamePattern(String answer, String word) {
        StringBuilder gamePattern = new StringBuilder(this.pattern);

        for (int i = 0; i < word.length(); i++) {
            if (gamePattern.charAt(i) == '+') {
                continue;
            }
            char wordChar = word.charAt(i);
            char answerChar = answer.charAt(i);

            if (answerChar == wordChar) {
                gamePattern.setCharAt(i, '+');
            } else if (answer.contains(String.valueOf(wordChar)) && gamePattern.charAt(i) != '+') {
                gamePattern.setCharAt(i, '^');
            } else {
                gamePattern.setCharAt(i, '-');
            }
        }
        this.pattern = gamePattern.toString();
    }

    private String calculateCleanPattern(String answer, String word) {
        StringBuilder cleanResult = new StringBuilder("-".repeat(answer.length()));
        List<Character> availableChars = new ArrayList<>();

        for (char c : answer.toCharArray()) {
            availableChars.add(c);
        }

        for (int i = 0; i < word.length(); i++) {
            char wordChar = word.charAt(i);
            char answerChar = answer.charAt(i);

            if (answerChar == wordChar) {
                cleanResult.setCharAt(i, '+');
                availableChars.remove(Character.valueOf(wordChar));
            } else if (availableChars.contains(wordChar)) {
                cleanResult.setCharAt(i, '^');
                availableChars.remove(Character.valueOf(wordChar));
            } else {
                cleanResult.setCharAt(i, '-');
            }
        }
        return cleanResult.toString();
    }
}
