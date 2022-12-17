package gab.service;

import gab.model.AnalyzedWord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnalyzerTest {

    private Analyzer analyzer;

    @BeforeEach
    public void beforeEach() {
        analyzer = new Analyzer();
    }

    @ParameterizedTest
    @ValueSource(strings = {"word", "different", "other"})
    public void analyze_OneWordGiven_ShouldReturnValidTokens(String word) {
        AnalyzedWord expected = createAnalyzedWord(word, 1, 1);
        List<AnalyzedWord> actual = analyzer.analyze(word);
        assertEquals(List.of(expected), actual);
    }

    private AnalyzedWord createAnalyzedWord(String word, int repetitions, Integer... positions) {
        return new AnalyzedWord(word, repetitions, new ArrayList<>(List.of(positions)));
    }

    @Test
    public void analyze_UniqueWordsGiven_ShouldReturnValidTokens() {
        List<AnalyzedWord> expected = List.of(
                createAnalyzedWord("aaa", 1, 1),
                createAnalyzedWord("bbb", 1, 2),
                createAnalyzedWord("ccc", 1, 3)
        );

        List<AnalyzedWord> actual = analyzer.analyze("aaa bbb ccc");
        assertEquals(expected, actual);
    }

    @Test
    public void analyze_WordsGiven_ShouldReturnSortedTokens() {
        List<AnalyzedWord> expected = List.of(
                new AnalyzedWord("a", 1, List.of(3)),
                new AnalyzedWord("b", 1, List.of(1)),
                new AnalyzedWord("c", 1, List.of(2))
        );
        List<AnalyzedWord> actual = analyzer.analyze("b c a");
        assertEquals(expected, actual);
    }

    @Test
    public void analyze_RepeatedWordsGiven_ShouldReturnOneToken() {
        List<AnalyzedWord> expected = List.of(
                new AnalyzedWord("word", 3, List.of(1, 2, 3))
        );
        List<AnalyzedWord> actual = analyzer.analyze("word word word");
        assertEquals(expected, actual);
    }

    @Test
    public void analyze_UniqueAndRepeatedWordsGiven_ShouldReturnValidTokens() {
        List<AnalyzedWord> expected = List.of(
                createAnalyzedWord("aaa", 2, 1, 3),
                createAnalyzedWord("bbb", 1, 2),
                createAnalyzedWord("ccc", 2, 4, 5)
        );
        List<AnalyzedWord> actual = analyzer.analyze("aaa bbb aaa ccc ccc");
        assertEquals(expected, actual);
    }

    @Test
    public void analyze_EmptyStringGiven_ShouldReturnEmptyList() {
        List<AnalyzedWord> actual = analyzer.analyze("");
        assertTrue(actual.isEmpty());
    }

    @Test
    public void analyze_PunctuationGiven_ShouldReturnEmptyList() {
        List<AnalyzedWord> actual = analyzer.analyze(",.:;?!-");
        assertTrue(actual.isEmpty());
    }

    @Test
    public void analyze_WordsAndPunctuationGiven_ShouldReturnThreeTokens() {
        List<AnalyzedWord> expected = List.of(
                createAnalyzedWord("aaa", 2, 1, 3),
                createAnalyzedWord("bbb", 1, 2)
        );
        List<AnalyzedWord> actual = analyzer.analyze(",aaa,. bbb:-aaa");
        assertEquals(expected, actual);
    }

    @Test
    public void analyze_TextWithNewLinesGiven_ShouldReturnTwoTokens() {
        List<AnalyzedWord> expected = List.of(
                createAnalyzedWord("aaa", 1, 1),
                createAnalyzedWord("bbb", 1, 2)
        );
        List<AnalyzedWord> actual = analyzer.analyze("aaa\nbbb");
        assertEquals(expected, actual);
    }

    @Test
    public void analyze_TheSameWordsWithDifferentCaseGiven_ShouldReturnOneToken() {
        List<AnalyzedWord> expected = List.of(
                createAnalyzedWord("aaa", 3, 1, 2, 3)
        );
        List<AnalyzedWord> actual = analyzer.analyze("aaa AAA aAa");
        assertEquals(expected, actual);
    }

    @Test
    public void analyze_PolishCharactersGiven_ShouldReturnTokenWithPolishCharacters() {
        List<AnalyzedWord> expected = List.of(
                createAnalyzedWord("gęślą", 1, 2),
                createAnalyzedWord("jaźń", 1, 3),
                createAnalyzedWord("zażółć", 1, 1)
                );
        List<AnalyzedWord> actual = analyzer.analyze("Zażółć gęślą jaźń");
        assertEquals(expected, actual);
    }
}
