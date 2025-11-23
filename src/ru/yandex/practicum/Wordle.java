package ru.yandex.practicum;
import ru.yandex.practicum.exceptions.NoAvailableWordsInDictionary;
import ru.yandex.practicum.exceptions.WordNotCorrectByRulesException;
import ru.yandex.practicum.printWriter.PrintWriter;
import ru.yandex.practicum.model.WordleDictionary;
import ru.yandex.practicum.model.WordleDictionaryLoader;
import ru.yandex.practicum.model.WordleGame;

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
    public static final int wordLength = 5;
    private static final int steps = 5;
    private static int step = 0;

    public static void main(String[] args) {
        PrintWriter printWriter = new PrintWriter("log.txt");
        WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(printWriter);
        Scanner scanner = new Scanner(System.in);

        try {
            WordleDictionary wordleDictionary = wordleDictionaryLoader.load("words_ru.txt", wordLength);
            WordleGame wordleGame = new WordleGame(wordleDictionary, printWriter);

            System.out.println("Добро пожаловать в Wordle!");
            System.out.println("Угадайте слово из 5 букв. У вас 6 попыток.");
            System.out.println("Символы: '+' - правильная позиция, '^' - есть буква, '-' - нет буквы");
            System.out.println("Для подсказки нажмите Enter без ввода слова. \n");

            String answer = wordleDictionary.getRandomWord();


            while (step < steps) {
                System.out.println("Введите слово:");
                String word = scanner.nextLine();

                if (word.isBlank()) {
                    try {
                       String hint = wordleGame.getHint(answer);
                       System.out.println("Попробуйте: " + hint);
                    } catch (NoAvailableWordsInDictionary exception) {
                        System.out.println("Нет доступных подсказок");
                    }
                    continue;
                }

                try {
                   word = wordleGame.getCorrectWord(word, wordLength);
                } catch (WordNotCorrectByRulesException | NoAvailableWordsInDictionary exception) {
                    System.out.println(exception.getMessage());
                    continue;
                }

                boolean isWon = wordleGame.checkForWinning(answer, word);
                if (isWon) {
                    System.out.println("Ты выиграл. Загаданное слово: " + answer);
                    return;
                }

                String pattern = wordleGame.updatePattern(answer, word);
                System.out.println(pattern);
                step++;
            }

            System.out.println("Ты проиграл. Загаданное слово: " + answer);
        } catch (Exception e) {
            printWriter.println(e.getMessage());
        }
    }



}
