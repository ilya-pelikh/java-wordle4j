package ru.yandex.practicum.model;

import ru.yandex.practicum.exceptions.NoAvailableHintsException;
import ru.yandex.practicum.exceptions.WordNotCorrectByRulesException;
import ru.yandex.practicum.exceptions.WordNotFoundInDictionaryException;

import java.util.ArrayList;
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
    private Scanner scanner;
    private String answer;
    private String pattern;

    private int steps;
    private int currentStep;

    private WordleDictionary dictionary;
    private ArrayList<String> usedWords;

    public WordleGame(WordleDictionary dictionary, Scanner scanner) {
        this.dictionary = dictionary;
        this.steps = 6;
        this.currentStep = 1;
        this.scanner = scanner;
        this.pattern = WordleDictionary.falsyPattern;
        this.usedWords = new ArrayList<>();
    }

    public void run() {
        System.out.println("Добро пожаловать в Wordle!");
        System.out.println("Угадайте слово из 5 букв. У вас 6 попыток.");
        System.out.println("Символы: '+' - правильная позиция, '^' - есть буква, '-' - нет буквы");
        System.out.println("Для подсказки нажмите Enter без ввода слова");
        answer = dictionary.getRandomWord();
        loop();
    }

    private void loop() {
        while (steps > currentStep) {
            System.out.println("Введите слово:");
            String currentWord = scanner.nextLine();
                try {
                    if (currentWord.isBlank()) {
                        currentWord = getHint();
                        System.out.println("Подсказка: " + currentWord);
                    }
                } catch (NoAvailableHintsException exception) {
                    System.out.println(exception.getMessage());
                    continue;
                }


            try {
                dictionary.getIsWordCorrect(currentWord);
            } catch (WordNotFoundInDictionaryException | WordNotCorrectByRulesException exception) {
                System.out.println(exception.getMessage());
            }

            String pattern = dictionary.getPattern(answer, currentWord);
            boolean isWon = dictionary.checkPatterForWinning(pattern);
            if (isWon) {
                System.out.println("Ты выиграл. Загаданное слово: " + answer);
                return;
            }

            this.pattern = pattern;
            usedWords.add(currentWord);
            currentStep++;

            System.out.println(pattern);
        }
        System.out.println("Ты проиграл. Загаданное слово: " + answer);
    }


    public String getHint() throws NoAvailableHintsException {
      String word = dictionary.getHintForPattern(answer, pattern, this.usedWords);
      if (word != null) {
          this.usedWords.add(word);
      }
      return word;
    }

}
