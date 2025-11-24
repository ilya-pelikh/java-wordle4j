package ru.yandex.practicum.model;
import ru.yandex.practicum.exceptions.DictionaryIsEmpty;
import ru.yandex.practicum.exceptions.NoAvailableWordsInDictionary;

import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final List<String> words;

    public WordleDictionary() {
        this.words = new ArrayList<>();
    }


    public String normalizeWord(String word) {
        return word.toLowerCase().replace("ё", "e");
    }

    public void addWord(String word) {
        words.add(normalizeWord(word));
    }

    public String getRandomWord() throws DictionaryIsEmpty {
        Random random = new Random();
        if (words.isEmpty()) {
            throw new DictionaryIsEmpty();
        }
        return words.get(random.nextInt(words.size()));
    }

    public String getWordByRegex(String regex, ArrayList<String> excluded) throws NoAvailableWordsInDictionary {
        for (String word : words) {
            if (!excluded.contains(word) && word.matches(regex)) {
                return word;
            }
        }

        throw new NoAvailableWordsInDictionary();
    }

    public List<String> getWords() {
        return words;
    }
}
