package ru.yandex.practicum.model;

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
    private String path;
    private Charset charset;
    private PrintWriter printWriter;

    public WordleDictionaryLoader(String path, PrintWriter printWriter) {
        this.path = path;
        this.charset = StandardCharsets.UTF_8;
        this.printWriter = printWriter;
    }

    public WordleDictionary createDictionary() {
        WordleDictionary wordleDictionary = new WordleDictionary();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(path, charset))) {
            while (fileReader.ready()) {
                String line = fileReader.readLine().trim();
                if (line.length() == WordleDictionary.wordSize) {
                    wordleDictionary.addWord(line);
                }
            }

        } catch (IOException e) {
            printWriter.println(e.getMessage());
        }

        return wordleDictionary;
    }

}
