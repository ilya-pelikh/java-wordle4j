package ru.yandex.practicum.model;

import ru.yandex.practicum.exceptions.DictionaryCouldNotLoad;
import ru.yandex.practicum.printWriter.PrintWriter;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.FileReader;
import java.io.IOException;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final Charset charset;
    PrintWriter printWriter;

    public WordleDictionaryLoader(PrintWriter printWriter) {
        this.charset = StandardCharsets.UTF_8;
        this.printWriter = printWriter;
    }

    public WordleDictionary load(String path, int wordLength) {
        WordleDictionary wordleDictionary = new WordleDictionary();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(path, charset))) {
            while (fileReader.ready()) {
                String line = fileReader.readLine().trim();
                if (line.length() == wordLength) {
                    wordleDictionary.addWord(line);
                }
            }

        } catch (IOException exception) {
            printWriter.println(new DictionaryCouldNotLoad().getMessage());
        }

        return wordleDictionary;
    }

}
