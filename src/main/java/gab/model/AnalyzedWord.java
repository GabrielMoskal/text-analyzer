package gab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class AnalyzedWord {
    @JsonProperty("slowo")
    private final String word;
    @JsonProperty("powtorzenia")
    private final int repetitions;
    @JsonProperty("pozycje")
    private final List<Integer> positions;

    public AnalyzedWord(String word, int repetitions, List<Integer> positions) {
        this.word = word;
        this.repetitions = repetitions;
        this.positions = List.copyOf(positions);
    }
}
