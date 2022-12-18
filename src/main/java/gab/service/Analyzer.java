package gab.service;

import gab.model.AnalyzedWord;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class Analyzer {
    public List<AnalyzedWord> analyze(@NonNull String text) {
        SortedMap<String, List<Integer>> words = prepareSortedWords(text);
        return wordMapToList(words);
    }

    private SortedMap<String, List<Integer>> prepareSortedWords(String text) {
        SortedMap<String, List<Integer>> words = new TreeMap<>();
        String[] wordArray = extractWords(text);
        for (int i = 0; i < wordArray.length; i++) {
            String key = wordArray[i];
            putOrReplace(words, key, i + 1);
        }
        return words;
    }

    private void putOrReplace(SortedMap<String, List<Integer>> words,
                              String key, int position) {
        if (words.containsKey(key)) {
            List<Integer> value = words.get(key);
            value.add(position);
        } else {
            List<Integer> value = new ArrayList<>();
            value.add(position);
            words.put(key, value);
        }
    }

    private String[] extractWords(String text) {
        String[] wordArray = text.toLowerCase()
                .replaceAll("\\p{Punct}|–", " ")
                .split("\\s+");
        return Stream.of(wordArray)
                .filter(word -> !word.isEmpty())
                .toArray(String[]::new);
    }

    private List<AnalyzedWord> wordMapToList(Map<String, List<Integer>> words) {
        List<AnalyzedWord> analyzedWords = new ArrayList<>();
        for (var entry : words.entrySet()) {
            var value = entry.getValue();
            analyzedWords.add(new AnalyzedWord(entry.getKey(),
                    value.size(), value));
        }
        return analyzedWords;
    }
}
