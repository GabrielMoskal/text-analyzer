package gab.service;

import gab.model.AnalyzedWord;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class Analyzer {
    public List<AnalyzedWord> analyze(@NonNull String text) {
        SortedMap<String, List<Integer>> words = new TreeMap<>();
        String[] wordArray = extractWords(text);

        for (int i = 0; i < wordArray.length; i++) {
            String key = wordArray[i];
            if (words.containsKey(key)) {
                List<Integer> value = words.get(key);
                value.add(i + 1);
            } else {
                List<Integer> value = new ArrayList<>();
                value.add(i + 1);
                words.put(key, value);
            }
        }
        List<AnalyzedWord> result = new ArrayList<>();
        for (var entry : words.entrySet()) {
            var value = entry.getValue();
            result.add(new AnalyzedWord(entry.getKey(), value.size(), value));
        }
        return result;
    }

    private static String[] extractWords(String text) {
        String[] wordArray = text.toLowerCase()
                .replaceAll("\\p{Punct}|–", " ").trim()
                .split("\\s+", 0);
        wordArray = Stream.of(wordArray)
                .filter(word -> !word.isEmpty())
                .toArray(String[]::new);
        return wordArray;
    }

}
