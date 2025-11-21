package ru.yandex.practicum;
import ru.yandex.practicum.model.PrintWriter;
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
    public static void main(String[] args) {
        PrintWriter printWriter = new PrintWriter("log.txt");
        Scanner scanner = new Scanner(System.in);

        try {
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader("words_ru.txt", printWriter);
            WordleDictionary wordleDictionary = wordleDictionaryLoader.createDictionary();
            WordleGame wordleGame = new WordleGame(wordleDictionary, scanner);

            wordleGame.run();
        } catch (Exception e) {
            printWriter.println("Unhandled error: " + e.getMessage());
        }
    }

}
