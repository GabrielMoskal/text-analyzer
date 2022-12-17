package gab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Text {

    @NotNull
    @JsonProperty("tekst")
    private String text;
}
