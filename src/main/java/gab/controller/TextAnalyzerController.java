package gab.controller;

import gab.model.AnalyzedWord;
import gab.model.Text;
import gab.service.Analyzer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@AllArgsConstructor
public class TextAnalyzerController {

    private Analyzer analyzer;

    @PostMapping("/analyze")
    public ResponseEntity<Collection<AnalyzedWord>> analyze(@RequestBody @Valid Text text) {
        Collection<AnalyzedWord> words = analyzer.analyze(text.getText());
        return new ResponseEntity<>(words, HttpStatus.OK);
    }
}
